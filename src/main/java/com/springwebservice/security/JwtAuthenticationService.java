package com.springwebservice.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwebservice.core.exception.SecurityException;
import com.springwebservice.core.utils.SecurityUtils;
import com.springwebservice.entity.PermissionEntity;
import com.springwebservice.entity.RoleEntity;
import com.springwebservice.security.model.UserAuthenticationModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
class JwtAuthenticationService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    static void createAccessToken(HttpServletResponse response, Authentication auth) throws SecurityException {
        log.debug("Generating access token!");

        byte[] asymmetricPrivateKey;

        try {
            asymmetricPrivateKey = SecurityUtils.getAsymmetricPrivateKeyAsByteArray();
        } catch (Exception e) {
            throw new SecurityException("Error to obtain asymmetric private key.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserAuthenticationModel userAuthenticationModel = (UserAuthenticationModel) auth.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userAuthenticationModel.getId());
        claims.put("name", userAuthenticationModel.getName());
        claims.put("email", userAuthenticationModel.getEmail());
        claims.put("roles", userAuthenticationModel.getRoles());
        claims.put("permissions", userAuthenticationModel.getPermissions());

        String token = Jwts.builder()
            .setSubject(userAuthenticationModel.getEmail())
            .setClaims(claims)
            .setExpiration(Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()))
            .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, asymmetricPrivateKey)
            .compact();

        try {
            response.getWriter().write("{\"access_token\":\"" + token + "\"}");
        } catch (Exception e) {
            throw new SecurityException("Error writing response token!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    static Authentication authenticateAccessToken(HttpServletRequest request) throws SecurityException {
        log.debug("Trying to authenticate token!");

        Claims tokenClaims;
        byte[] asymmetricPrivateKey;

        if (StringUtils.isBlank(request.getHeader(AUTHORIZATION))) {
            throw new SecurityException("No authorization header found!", HttpStatus.UNAUTHORIZED);
        }

        try {
            asymmetricPrivateKey = SecurityUtils.getAsymmetricPrivateKeyAsByteArray();
        } catch (Exception e) {
            throw new SecurityException("Error to obtain asymmetric private key!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            tokenClaims = Jwts.parser().setSigningKey(asymmetricPrivateKey).parseClaimsJws(request.getHeader(AUTHORIZATION).replace(TOKEN_PREFIX, StringUtils.EMPTY)).getBody();
        } catch (Exception e) {
            throw new SecurityException("Error to parse token claims! Token can't be trusted!", HttpStatus.UNAUTHORIZED);
        }

        UserAuthenticationModel userAuthenticationModel = new UserAuthenticationModel();
        userAuthenticationModel.setId(Long.valueOf(String.valueOf(tokenClaims.get("id"))));
        userAuthenticationModel.setName(String.valueOf(tokenClaims.get("name")));
        userAuthenticationModel.setName(String.valueOf(tokenClaims.get("email")));

        ObjectMapper mapper = new ObjectMapper();
        userAuthenticationModel.setRoles(mapper.convertValue(tokenClaims.get("roles"), new TypeReference<Set<RoleEntity>>(){}));
        userAuthenticationModel.setPermissions(mapper.convertValue(tokenClaims.get("permissions"), new TypeReference<Set<PermissionEntity>>(){}));

        Authentication auth = new UsernamePasswordAuthenticationToken(userAuthenticationModel, userAuthenticationModel.getRoles(), userAuthenticationModel.getPermissions());

        if (LocalDateTime.ofInstant(tokenClaims.getExpiration().toInstant(), ZoneId.systemDefault()).isBefore(LocalDateTime.now())) {
            log.debug("Expired token!");
            throw new SecurityException("Expired token!", HttpStatus.UNAUTHORIZED);
        }

        return auth;
    }

}
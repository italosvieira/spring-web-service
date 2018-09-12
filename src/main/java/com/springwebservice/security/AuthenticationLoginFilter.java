package com.springwebservice.security;

import com.springwebservice.core.exception.SecurityException;
import com.springwebservice.core.utils.SecurityUtils;
import com.springwebservice.security.model.UserAuthenticationModel;
import com.springwebservice.service.interfaces.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationService authenticationService;

    AuthenticationLoginFilter(String loginUrl, AuthenticationManager authManager, AuthenticationService authenticationService) {
        super(loginUrl);
        this.setAuthenticationManager(authManager);
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws SecurityException {
        if (!SecurityUtils.isMethodPost(request.getMethod())) {
            throw new SecurityException("Method not allowed: " +  request.getMethod(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        UserAuthenticationModel userAuthenticationModel = this.authenticationService.authenticateUser(request);
        return new UsernamePasswordAuthenticationToken(userAuthenticationModel, userAuthenticationModel.getRoles(), userAuthenticationModel.getPermissions());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication auth) throws SecurityException {
        try {
            JwtAuthenticationService.createAccessToken(response, auth);
        } catch (SecurityException e) {
            this.unsuccessfulAuthentication(request, response, e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws SecurityException {
        this.getRememberMeServices().loginFail(request, response);
        SecurityUtils.writeErrorResponse(request, response, failed);
    }

}
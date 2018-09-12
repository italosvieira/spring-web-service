package com.springwebservice.security;

import com.springwebservice.core.exception.SecurityException;
import com.springwebservice.core.utils.SecurityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationValidatorFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws SecurityException, IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            SecurityContextHolder.getContext().setAuthentication(JwtAuthenticationService.authenticateAccessToken(httpServletRequest));
        } catch (Exception e) {
            SecurityUtils.writeErrorResponse(httpServletRequest, httpServletResponse, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
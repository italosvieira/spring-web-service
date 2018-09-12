package com.springwebservice.service.interfaces;

import com.springwebservice.core.exception.SecurityException;
import com.springwebservice.security.model.UserAuthenticationModel;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    UserAuthenticationModel authenticateUser(HttpServletRequest request) throws SecurityException;
}
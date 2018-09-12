package com.springwebservice.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class SecurityException extends AuthenticationException {

    private static final long serialVersionUID = -4878995800736114676L;

    private HttpStatus httpStatus;

    public SecurityException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

}
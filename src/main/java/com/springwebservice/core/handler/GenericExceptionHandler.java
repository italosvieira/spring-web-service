package com.springwebservice.core.handler;

import com.springwebservice.core.exception.BusinessException;
import com.springwebservice.core.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG = "Default error handler for: ";
    private static final String PATH = ". On path: ";

    @ExceptionHandler(Exception.class)
    public ResponseEntity defaultErrorHandler(Exception exception, WebRequest webRequest) {
        String path = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        String msg = exception.getMessage();

        logger.error(MSG + msg + PATH + path);

        if (exception instanceof BusinessException) {
            HttpStatus httpStatus = ((BusinessException) exception).getHttpStatus();
            return ResponseEntity.status(httpStatus).body(SecurityUtils.createErrorBody(msg, httpStatus, path));
        } else if (exception instanceof AccessDeniedException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SecurityUtils.createErrorBody(msg, HttpStatus.FORBIDDEN, path));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SecurityUtils.createErrorBody(msg, HttpStatus.INTERNAL_SERVER_ERROR, path));
    }

}
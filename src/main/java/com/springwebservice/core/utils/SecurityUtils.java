package com.springwebservice.core.utils;

import com.springwebservice.core.exception.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
public class SecurityUtils {

    public static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private static final String TIMESTAMP = "  \"timestamp\": \"";
    private static final String STATUS = "  \"status\": \"";
    private static final String ERROR = "  \"error\": \"";
    private static final String PATH = "  \"path\": \"";
    private static final String MESSAGE = "  \"message\": \"";
    private static final String OPEN_CURLY_BRACKET = "{";
    private static final String CLOSE_CURLY_BRACKET = "}";
    private static final String CLOSE_QM_WITH_COMA = "\",";
    private static final String CLOSE_QM = "\"";

    public static byte[] getAsymmetricPrivateKeyAsByteArray() throws Exception {
        return StreamUtils.copyToByteArray(new ClassPathResource(PropertyUtils.asymmetricPrivateKeyFileName).getInputStream());
    }

    public static Boolean isMethodPost(String method) {
        return HttpMethod.POST.toString().equalsIgnoreCase(method);
    }

    public static void writeErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        log.error(exception.getMessage(), exception);

        try {
            SecurityContextHolder.clearContext();

            SecurityException securityException;

            if (exception instanceof SecurityException) {
                securityException = (SecurityException) exception;
            } else {
                securityException = new SecurityException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            response.setStatus(securityException.getHttpStatus().value());
            response.getWriter().write(createErrorBody(securityException.getMessage(), securityException.getHttpStatus(), request.getRequestURI()));
        } catch (Exception e) {
            log.error("Failed to send error response!", e);
        }
    }

    public static String createErrorBody(String msg, HttpStatus status, String requestURI) {
        String lineSeparator = System.lineSeparator();

        return OPEN_CURLY_BRACKET + lineSeparator +
            TIMESTAMP + LocalDateTime.now().toString() + CLOSE_QM_WITH_COMA + lineSeparator +
            STATUS + status.value() + CLOSE_QM_WITH_COMA + lineSeparator +
            ERROR  + status.getReasonPhrase() + CLOSE_QM_WITH_COMA + lineSeparator +
            PATH + requestURI + CLOSE_QM_WITH_COMA + lineSeparator +
            MESSAGE + msg + CLOSE_QM + lineSeparator +
            CLOSE_CURLY_BRACKET;
    }

}
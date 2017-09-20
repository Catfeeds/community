package com.tongwii.core;

import com.tongwii.constant.ResultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception Handler Controller Advice to catch all controller exceptions and respond gracefully to
 * the caller
 */
@ControllerAdvice
public class WebAppExceptionAdvice extends ResponseEntityExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(WebAppExceptionAdvice.class);


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> processParameterizedValidationError(RuntimeException ex) {
        return new ResponseEntity<>(Result.errorResult(ResultConstants.ERR_INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> processAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(Result.errorResult(ResultConstants.ERR_ACCESS_DENIED), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> precessBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(Result.errorResult(ResultConstants.ERR_BAD_CREDENTIALS), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> processException(Exception ex) {
        if (log.isDebugEnabled()) {
            log.debug("An unexpected error occurred: {}", ex.getMessage(), ex);
        } else {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
        return new ResponseEntity<>(Result.errorResult(ResultConstants.ERR_INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.tongwii.core;

import com.tongwii.constant.ResultConstants;
import com.tongwii.core.exception.BadRequestAlertException;
import com.tongwii.security.UserNotActivatedException;
import com.tongwii.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

/**
 * Exception Handler Controller Advice to catch all controller exceptions and respond gracefully to
 * the caller
 */
@ControllerAdvice
public class WebAppExceptionAdvice extends ResponseEntityExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(WebAppExceptionAdvice.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> processAuthException(AuthenticationException ex) {
        return new ResponseEntity<>(ResultConstants.ERR_AUTHENTICATION + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> processNullPointException(NullPointerException ex) {
        return new ResponseEntity<>(ResultConstants.ERR_NULL_POINT + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> processDisabledException(DisabledException ex) {
        return new ResponseEntity<>(ResultConstants.ERR_USER_DISABLED + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> processValidateException(ValidationException ex) {
        return new ResponseEntity<>(ResultConstants.ERR_VALIDATION + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> processAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(ResultConstants.ERR_ACCESS_DENIED + e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> precessBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(ResultConstants.ERR_BAD_CREDENTIALS + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<Object> precessUserNotActivatedException(UserNotActivatedException e) {
        return new ResponseEntity<>(ResultConstants.ERR_USERNOTACTIVATED + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity handleBadRequestAlertException(BadRequestAlertException ex) {
        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ex.getEntityName(), ex.getErrorKey(), ex.getMessage())).body(ex);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Object> processException(Exception ex) {
        if (log.isDebugEnabled()) {
            log.debug("An unexpected error occurred: {}", ex.getMessage(), ex);
        } else {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
        return new ResponseEntity<>(ResultConstants.ERR_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

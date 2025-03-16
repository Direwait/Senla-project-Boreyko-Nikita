package com.example.advice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        log.error("Conflict error || URI: {} || Message: {}",
                request.getDescription(false), ex.getMessage(), ex);

        return handleExceptionInternal(ex, "Operation conflict",
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        log.warn("Entity not found || URI: {} || Message: {}",
                request.getDescription(false), ex.getMessage());

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        log.error("Access denied || URI: {} || Message: {}",
                request.getDescription(false), ex.getMessage(), ex);

        return handleExceptionInternal(ex, "Access denied",
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthException(AuthenticationException ex, WebRequest request) {
        log.error("Authentication failed || URI: {} || Message: {}",
                request.getDescription(false), ex.getMessage(), ex);

        return handleExceptionInternal(ex, "Authentication failed",
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unhandled exception || URI: {} || Message: {}",
                request.getDescription(false), ex.getMessage(), ex);

        return handleExceptionInternal(ex, "Internal server error",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

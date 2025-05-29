
package com.neo_educ.backend.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex) {
        return ApiException.toResponseEntity(ex);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.UNAUTHORIZED,
                "Email não registrado"
        );
        return ApiException.toResponseEntity(apiException);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.UNAUTHORIZED,
                "Credenciais Inválidas"
        );
        return ApiException.toResponseEntity(apiException);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex);
        return ApiException.toResponseEntity(apiException);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleJwtException(JwtException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.UNAUTHORIZED,
                "Token Inválido"
        );
        return ApiException.toResponseEntity(apiException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex.getBindingResult() != null) {
            ex.getBindingResult().getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationException(ConstraintViolationException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex);
        return ApiException.toResponseEntity(apiException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado");
        return ApiException.toResponseEntity(apiException);
    }


}

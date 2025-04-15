package com.neo_educ.backend.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, ex);
        return ApiException.toResponseEntity(apiException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", ex);
        return ApiException.toResponseEntity(apiException);
    }




}

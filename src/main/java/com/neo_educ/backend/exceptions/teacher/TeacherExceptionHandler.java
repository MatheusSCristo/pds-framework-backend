package com.neo_educ.backend.exceptions.teacher;

import com.neo_educ.backend.exceptions.ApiException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TeacherExceptionHandler {

    @ExceptionHandler(TeacherAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleTeacherAlreadyRegisteredException(TeacherAlreadyRegisteredException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex);
        return ApiException.toResponseEntity(apiException);
    }
}

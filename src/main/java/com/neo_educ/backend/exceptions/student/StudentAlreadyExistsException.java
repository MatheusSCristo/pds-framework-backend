package com.neo_educ.backend.exceptions.student;

import com.neo_educ.backend.exceptions.CustomHttpStatusException;
import org.springframework.http.HttpStatus;

public class StudentAlreadyExistsException extends RuntimeException implements CustomHttpStatusException {
    public StudentAlreadyExistsException() {
        super("Este estudante já está cadastrado");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

package com.neo_educ.backend.exceptions.student;

import com.neo_educ.backend.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class StudentAlreadyExistsException extends ApiException {
    public StudentAlreadyExistsException() {
        super(
                HttpStatus.BAD_REQUEST,"Este estudante já está cadastrado");
    }

}

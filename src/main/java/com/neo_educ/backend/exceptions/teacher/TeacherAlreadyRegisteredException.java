package com.neo_educ.backend.exceptions.teacher;

import com.neo_educ.backend.exceptions.CustomHttpStatusException;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class TeacherAlreadyRegisteredException extends RuntimeException implements CustomHttpStatusException {
    public TeacherAlreadyRegisteredException() {
        super("Email já cadastrado");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

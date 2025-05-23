package com.neo_educ.backend.exceptions.teacher;

import com.neo_educ.backend.exceptions.CustomHttpStatusException;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class TeacherAlreadyRegisteredException extends RuntimeException implements CustomHttpStatusException {
    public TeacherAlreadyRegisteredException(String email) {
        super("Teacher with email " + email + " already registered");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

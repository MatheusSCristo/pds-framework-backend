package com.neo_educ.backend.exceptions.teacher;

import com.neo_educ.backend.exceptions.ApiException;
import org.springframework.http.HttpStatus;


public class TeacherAlreadyRegisteredException extends ApiException {
    public TeacherAlreadyRegisteredException() {
        super(HttpStatus.BAD_REQUEST,"Email já cadastrado");
    }


}

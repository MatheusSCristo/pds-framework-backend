package com.neo_educ.backend.exceptions.patient;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PatientAlreadyExistsException extends RuntimeException {
    public PatientAlreadyExistsException() {
        super("Um paciente com este e-mail já está cadastrado para este nutricionista.");
    }
}
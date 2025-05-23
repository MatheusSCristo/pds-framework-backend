package com.neo_educ.backend.exceptions.notes;

import com.neo_educ.backend.exceptions.CustomHttpStatusException;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class DuplicateNoteTitleException extends RuntimeException implements CustomHttpStatusException {
    public DuplicateNoteTitleException() {
        super("Já existe uma nota com este nome para este aluno");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

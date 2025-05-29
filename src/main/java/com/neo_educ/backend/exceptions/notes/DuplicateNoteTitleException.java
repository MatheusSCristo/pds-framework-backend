package com.neo_educ.backend.exceptions.notes;

import com.neo_educ.backend.exceptions.ApiException;
import org.springframework.http.HttpStatus;


public class DuplicateNoteTitleException extends ApiException {
    public DuplicateNoteTitleException() {
        super( HttpStatus.BAD_REQUEST,"Já existe uma nota com este nome para este aluno");
    }

}

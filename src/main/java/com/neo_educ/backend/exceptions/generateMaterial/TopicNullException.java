package com.neo_educ.backend.exceptions.generateMaterial;

import com.neo_educ.backend.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class TopicNullException extends ApiException {
    public TopicNullException() {
        super(HttpStatus.BAD_REQUEST,"O campo [tópico] não pode ser nulo");
    }
}

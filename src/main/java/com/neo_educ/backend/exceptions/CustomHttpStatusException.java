package com.neo_educ.backend.exceptions;

import org.springframework.http.HttpStatus;

public interface CustomHttpStatusException {
    HttpStatus getStatus();
}

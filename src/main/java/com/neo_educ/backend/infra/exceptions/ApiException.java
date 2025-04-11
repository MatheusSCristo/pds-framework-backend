package com.neo_educ.backend.infra.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "localizedMessage"})
public class ApiException extends RuntimeException {
    private HttpStatus status;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;

    private ApiException() {
        timestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    public ApiException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getMessage();
    }

    public ApiException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }

    public static ResponseEntity<Object> toResponseEntity(ApiException ex) {
        return new ResponseEntity<Object>(ex, ex.status);
    }
}
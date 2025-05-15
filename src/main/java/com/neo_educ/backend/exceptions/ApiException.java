package com.neo_educ.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "localizedMessage"})
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp;

    public ApiException(HttpStatus status, Throwable ex) {
        super(ex.getMessage(), ex);
        this.status = status;
        this.message = ex.getMessage();
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    public ApiException(HttpStatus status, String message, Throwable ex) {
        super(message, ex);
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    public static ResponseEntity<Object> toResponseEntity(ApiException ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }
}

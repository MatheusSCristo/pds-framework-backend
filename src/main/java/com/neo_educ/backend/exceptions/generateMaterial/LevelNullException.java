package com.neo_educ.backend.exceptions.generateMaterial;

import com.neo_educ.backend.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class LevelNullException extends ApiException {
  public LevelNullException() {
    super(HttpStatus.BAD_REQUEST, "O campo [nível] não pode ser nulo");
  }
}

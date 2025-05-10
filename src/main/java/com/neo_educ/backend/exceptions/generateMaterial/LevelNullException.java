package com.neo_educ.backend.exceptions.generateMaterial;

public class LevelNullException extends RuntimeException {
  public LevelNullException() {
    super("O campo [nível] não pode ser nulo");
  }
}

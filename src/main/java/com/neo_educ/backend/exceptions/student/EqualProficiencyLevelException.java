package com.neo_educ.backend.exceptions.student;

public class EqualProficiencyLevelException extends RuntimeException {
  public EqualProficiencyLevelException() {
    super("Este já é o nível atual de proeficiência do aluno");
  }
}

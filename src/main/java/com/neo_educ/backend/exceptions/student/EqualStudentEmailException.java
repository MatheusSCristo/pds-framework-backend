package com.neo_educ.backend.exceptions.student;

public class EqualStudentEmailException extends RuntimeException {
  public EqualStudentEmailException() {
    super("Este email já está em uso");
  }
}

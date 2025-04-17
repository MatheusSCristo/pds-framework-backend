package com.neo_educ.backend.exceptions.student;

public class StudentNotFoundException extends RuntimeException {
  public StudentNotFoundException() {
    super("O estudante não existe");
  }
}

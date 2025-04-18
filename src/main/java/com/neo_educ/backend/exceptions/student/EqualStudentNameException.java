package com.neo_educ.backend.exceptions.student;

public class EqualStudentNameException extends RuntimeException {
  public EqualStudentNameException() {
    super("Este nome já está em uso");
  }
}

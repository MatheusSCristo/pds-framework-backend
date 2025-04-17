package com.neo_educ.backend.exceptions.student;

public class StudentAlreadyExistsException extends RuntimeException {
  public StudentAlreadyExistsException() {
    super("Este estudante já está cadastrado");
  }
}

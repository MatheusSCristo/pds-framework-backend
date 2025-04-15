package com.neo_educ.backend.exceptions.teacher;

public class TeacherAlreadyRegisteredException extends RuntimeException {
  public TeacherAlreadyRegisteredException(String email) {
    super("Teacher with email " + email + " already registered");
  }
}

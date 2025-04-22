package com.neo_educ.backend.exceptions.student;

public class InvalidUpdateStudentInformationsRequestException extends RuntimeException {
  public InvalidUpdateStudentInformationsRequestException() {
    super("Os campos [name], [email] e [proficiencyLevel] não podem ser simultaneamente nulos");
  }
}

package com.neo_educ.backend.exceptions.notes;

public class DuplicateNoteTitleException extends RuntimeException {
  public DuplicateNoteTitleException() {
    super("Já existe uma nota com este nome para este aluno");
  }
}

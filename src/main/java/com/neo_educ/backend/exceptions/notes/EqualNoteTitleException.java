package com.neo_educ.backend.exceptions.notes;

public class EqualNoteTitleException extends RuntimeException {
  public EqualNoteTitleException() {
    super("Este título já está em uso");
  }
}

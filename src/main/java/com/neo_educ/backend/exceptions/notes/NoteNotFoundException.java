package com.neo_educ.backend.exceptions.notes;

public class NoteNotFoundException extends RuntimeException {
  public NoteNotFoundException() {
    super("Nota não encontrada");
  }
}

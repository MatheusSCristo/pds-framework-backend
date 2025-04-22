package com.neo_educ.backend.exceptions.notes;

public class EqualNoteContentException extends RuntimeException {
  public EqualNoteContentException() {
    super("Este já é o conteúdo da nota");
  }
}

package com.neo_educ.backend.exceptions.notes;

public class InvalidUpdateNoteInformationsRequestException extends RuntimeException {
  public InvalidUpdateNoteInformationsRequestException() {
    super("Os campos [title] e [content] não podem ser simultaneamente nulos");
  }
}

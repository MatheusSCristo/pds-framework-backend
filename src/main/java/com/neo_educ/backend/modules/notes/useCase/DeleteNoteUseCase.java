package com.neo_educ.backend.modules.notes.useCase;

import com.neo_educ.backend.exceptions.notes.NoteNotFoundException;
import com.neo_educ.backend.modules.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteNoteUseCase {

  @Autowired
  private NotesRepository notesRepository;

  public void execute(String teacherEmail, Long studentId, Long noteId) {
    var note = notesRepository.findByIdAndTeacherEmailAndStudentId(noteId, teacherEmail, studentId)
        .orElseThrow(NoteNotFoundException::new);

    notesRepository.delete(note);
  }

}

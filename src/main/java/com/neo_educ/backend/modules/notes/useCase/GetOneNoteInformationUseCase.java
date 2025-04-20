package com.neo_educ.backend.modules.notes.useCase;

import com.neo_educ.backend.exceptions.notes.NoteNotFoundException;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.modules.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetOneNoteInformationUseCase {

  @Autowired
  private NotesRepository notesRepository;

  public NotesResponseDTO execute(String teacherEmail, Long studentId, Long noteId) {
    NotesEntity entity = notesRepository.findByIdAndTeacherEmailAndStudentId(noteId, teacherEmail, studentId)
        .orElseThrow(NoteNotFoundException::new);

    return NotesResponseDTO.fromEntity(entity);
  }


}

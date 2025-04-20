package com.neo_educ.backend.modules.notes.useCase;

import com.neo_educ.backend.exceptions.notes.EqualNoteContentException;
import com.neo_educ.backend.exceptions.notes.EqualNoteTitleException;
import com.neo_educ.backend.exceptions.notes.InvalidUpdateNoteInformationsRequestException;
import com.neo_educ.backend.exceptions.notes.NoteNotFoundException;
import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.modules.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateNoteInformationsUseCase {

  @Autowired
  private NotesRepository notesRepository;

  public NotesResponseDTO execute(String teacherEmail, Long studentId, Long noteId, NotesRequestDTO notesRequestDTO) {
    if(notesRequestDTO.title() == null && notesRequestDTO.content() == null) {
      throw new InvalidUpdateNoteInformationsRequestException();
    }

    NotesEntity note = this.notesRepository.findByIdAndTeacherEmailAndStudentId(noteId, teacherEmail, studentId)
        .orElseThrow(NoteNotFoundException::new);

    if(notesRequestDTO.title() != null) {
      if(notesRequestDTO.title().equals(note.getTitle())) {
        throw new EqualNoteTitleException();
      }

      note.setTitle(notesRequestDTO.title());
    }

    if(notesRequestDTO.content() != null) {
      if(notesRequestDTO.content().equals(note.getContent())) {
        throw new EqualNoteContentException();
      }

      note.setContent(notesRequestDTO.content());
    }

    note.setUpdatedAt(LocalDateTime.now());

    NotesEntity updatedNoteInformations = this.notesRepository.save(note);

    return NotesResponseDTO.fromEntity(updatedNoteInformations);
  }

}

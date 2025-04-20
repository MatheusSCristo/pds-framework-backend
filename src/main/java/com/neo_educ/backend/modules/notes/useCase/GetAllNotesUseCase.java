package com.neo_educ.backend.modules.notes.useCase;

import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.modules.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllNotesUseCase {

  @Autowired
  private NotesRepository notesRepository;

  public List<NotesResponseDTO> execute(String teacherEmail, Long studentId) {
    List<NotesEntity> notes = notesRepository.findAll().stream()
        .filter(note -> note.getTeacherEmail().equals(teacherEmail) && note.getStudentId().equals(studentId))
        .toList();

    return notes.stream()
        .map(NotesResponseDTO::fromEntity)
        .toList();
  }

}

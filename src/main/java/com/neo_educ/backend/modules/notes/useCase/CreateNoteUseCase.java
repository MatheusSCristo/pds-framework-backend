package com.neo_educ.backend.modules.notes.useCase;

import com.neo_educ.backend.exceptions.notes.DuplicateNoteTitleException;
import com.neo_educ.backend.exceptions.student.StudentNotFoundException;
import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.modules.notes.repository.NotesRepository;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateNoteUseCase {

  @Autowired
  private NotesRepository notesRepository;

  @Autowired
  private StudentRepository studentRepository;

  public NotesResponseDTO execute(String teacherEmail, Long studentId, NotesRequestDTO notesRequestDTO) {
    studentRepository.findByIdAndTeacherEmail(studentId, teacherEmail)
        .orElseThrow(StudentNotFoundException::new);

    if(notesRepository.findByTitleAndTeacherEmailAndStudentId(notesRequestDTO.title(), teacherEmail, studentId)
        .isPresent()) {
      throw new DuplicateNoteTitleException();
    }

    NotesEntity entity = NotesEntity.builder()
        .title(notesRequestDTO.title())
        .content(notesRequestDTO.content())
        .teacherEmail(teacherEmail)
        .studentId(studentId)
        .build();

    NotesEntity saved = notesRepository.save(entity);

    return NotesResponseDTO.fromEntity(saved);
  }

}

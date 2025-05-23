package com.neo_educ.backend.modules.notes.service;

import com.neo_educ.backend.exceptions.notes.DuplicateNoteTitleException;
import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.modules.notes.mapper.NotesMapper;
import com.neo_educ.backend.modules.notes.repository.NotesRepository;
import com.neo_educ.backend.modules.student.mapper.StudentMapper;
import com.neo_educ.backend.modules.student.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private NotesMapper notesMapper;

    public NotesResponseDTO createNote(String teacherEmail, Long studentId, NotesRequestDTO notesRequestDTO) {
        studentService.findStudent(studentId);
        if (notesRepository.findByTitleAndTeacherEmailAndStudentId(notesRequestDTO.title(), teacherEmail, studentId)
                .isPresent()) {
            throw new DuplicateNoteTitleException();
        }

        NotesEntity entity = notesMapper.toEntity(notesRequestDTO);
        notesRepository.save(entity);
        return NotesResponseDTO.fromEntity(entity);
    }

    public void deleteNote(Long noteId) {
        notesRepository.deleteById(noteId);
    }

    public List<NotesResponseDTO> findAllNotes(Long studentId) {
        List<NotesEntity> notes = notesRepository.findAllByStudentId(studentId);
        return notes.stream()
                .map(NotesResponseDTO::fromEntity)
                .toList();
    }

    public NotesResponseDTO findNote(Long noteId) {
        NotesEntity entity = notesRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma nota encontrada com ID: " + noteId));

        return NotesResponseDTO.fromEntity(entity);
    }
}

package com.neo_educ.backend.modules.notes.service;

import com.neo_educ.backend.exceptions.notes.DuplicateNoteTitleException;
import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.modules.notes.mapper.NotesMapper;
import com.neo_educ.backend.modules.notes.repository.NotesRepository;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
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

    public NotesResponseDTO createNote(Long studentId, NotesRequestDTO notesRequestDTO) {
        StudentEntity student=studentService.findStudent(studentId);
        if (notesRepository.findByTitleAndStudentId(notesRequestDTO.title(), studentId)
                .isPresent()) {
            throw new DuplicateNoteTitleException();
        }

        NotesEntity entity = notesMapper.toEntity(notesRequestDTO);
        entity.setStudent(student);
        notesRepository.save(entity);
        return notesMapper.toResponseDTO(entity);
    }

    public void deleteNote(Long noteId) {
        notesRepository.deleteById(noteId);
    }

    public List<NotesResponseDTO> findAllNotes(Long studentId) {
        List<NotesEntity> notes = notesRepository.findAllByStudentId(studentId);
        return notes.stream()
                .map((item)-> notesMapper.toResponseDTO(item))
                .toList();
    }

    public NotesResponseDTO findNote(Long noteId) {
        NotesEntity entity = notesRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma nota encontrada com ID: " + noteId));

        return notesMapper.toResponseDTO(entity);
    }
}

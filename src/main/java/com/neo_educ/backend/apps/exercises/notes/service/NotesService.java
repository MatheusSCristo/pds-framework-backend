package com.neo_educ.backend.apps.exercises.notes.service;

import com.neo_educ.backend.apps.exercises.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.apps.exercises.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.apps.exercises.notes.entity.NotesEntity;
import com.neo_educ.backend.apps.exercises.notes.mapper.NotesMapper;
import com.neo_educ.backend.apps.exercises.notes.repository.NotesRepository;
import com.neo_educ.backend.apps.exercises.athlete.entity.StudentEntity;
import com.neo_educ.backend.apps.exercises.athlete.repository.StudentRepository;
import com.neo_educ.backend.exceptions.notes.DuplicateNoteTitleException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private NotesMapper notesMapper;

    public NotesResponseDTO createNote(Long studentId, NotesRequestDTO notesRequestDTO) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
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

package com.neo_educ.backend.apps.exercises.notes.service;

import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.apps.exercises.athlete.repository.AthleteRepository;
import com.neo_educ.backend.apps.exercises.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.apps.exercises.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.apps.exercises.notes.entity.AthleteNotesEntity;
import com.neo_educ.backend.apps.exercises.notes.mapper.AthleteNotesMapper;
import com.neo_educ.backend.apps.exercises.notes.repository.AthleteNotesRepository;
import com.neo_educ.backend.exceptions.notes.DuplicateNoteTitleException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalNotesService {

    @Autowired
    private AthleteNotesRepository notesRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private AthleteNotesMapper notesMapper;

    public NotesResponseDTO createNote(Long athleteId, NotesRequestDTO notesRequestDTO) {
        AthleteEntity athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + athleteId));
        if (notesRepository.findByTitleAndAthleteId(notesRequestDTO.title(), athleteId)
                .isPresent()) {
            throw new DuplicateNoteTitleException();
        }

        AthleteNotesEntity entity = notesMapper.toEntity(notesRequestDTO);
        entity.setAthlete(athlete);
        notesRepository.save(entity);
        return notesMapper.toResponseDTO(entity);
    }

    public void deleteNote(Long noteId) {
        notesRepository.deleteById(noteId);
    }

    public List<NotesResponseDTO> findAllNotes(Long athleteId) {
        List<AthleteNotesEntity> notes = notesRepository.findAllByAthleteId(athleteId);
        return notes.stream()
                .map((item)-> notesMapper.toResponseDTO(item))
                .toList();
    }

    public NotesResponseDTO findNote(Long noteId) {
        AthleteNotesEntity entity = notesRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma nota encontrada com ID: " + noteId));

        return notesMapper.toResponseDTO(entity);
    }
}

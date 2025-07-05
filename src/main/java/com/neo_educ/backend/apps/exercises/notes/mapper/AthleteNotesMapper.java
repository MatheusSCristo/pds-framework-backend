package com.neo_educ.backend.apps.exercises.notes.mapper;

import com.neo_educ.backend.apps.exercises.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.apps.exercises.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.apps.exercises.notes.entity.AthleteNotesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AthleteNotesMapper {

    AthleteNotesEntity toEntity(NotesRequestDTO notesRequestDTO);

    NotesResponseDTO toResponseDTO(AthleteNotesEntity notesEntity);
}

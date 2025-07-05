package com.neo_educ.backend.apps.exercises.notes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.neo_educ.backend.apps.english.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.apps.english.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.apps.english.notes.entity.NotesEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotesMapper {

    NotesEntity toEntity(NotesRequestDTO notesRequestDTO);

    NotesResponseDTO toResponseDTO(NotesEntity notesEntity);
}

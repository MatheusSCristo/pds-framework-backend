package com.neo_educ.backend.apps.english.classplans.notes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.neo_educ.backend.apps.english.classplans.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.apps.english.classplans.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.apps.english.classplans.notes.entity.NotesEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotesMapper {

    NotesEntity toEntity(NotesRequestDTO notesRequestDTO);

    NotesResponseDTO toResponseDTO(NotesEntity notesEntity);
}

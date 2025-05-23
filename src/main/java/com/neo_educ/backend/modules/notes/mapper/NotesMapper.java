package com.neo_educ.backend.modules.notes.mapper;

import com.neo_educ.backend.modules.notes.dto.NotesRequestDTO;
import com.neo_educ.backend.modules.notes.dto.NotesResponseDTO;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotesMapper {

    NotesEntity toEntity(NotesRequestDTO notesRequestDTO);

    NotesResponseDTO toResponseDTO(NotesEntity notesEntity);
}

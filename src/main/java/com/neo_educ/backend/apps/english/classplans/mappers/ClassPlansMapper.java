package com.neo_educ.backend.apps.english.classplans.mappers;

import org.mapstruct.Mapper;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.apps.english.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.core.mapper.SessionMapper;

@Mapper(componentModel = "spring")
public interface ClassPlansMapper extends SessionMapper<ClassPlansCreateDTO, ClassPlansResponseDTO, ClassPlansEntity> {
    @Override
    ClassPlansResponseDTO toResponse(ClassPlansEntity entity);

    @Override
    ClassPlansEntity toEntity(ClassPlansCreateDTO createDto);
}
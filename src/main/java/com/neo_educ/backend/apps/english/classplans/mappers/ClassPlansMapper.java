package com.neo_educ.backend.apps.english.classplans.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.apps.english.classplans.entity.ClassPlansEntity;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassPlansMapper {

    ClassPlansResponseDTO toResponse(ClassPlansEntity entity);


}

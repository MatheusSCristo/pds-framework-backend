package com.neo_educ.backend.apps.english.studentActivity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.neo_educ.backend.apps.english.studentActivity.dto.StudentActivityCreateDTO;
import com.neo_educ.backend.apps.english.studentActivity.dto.StudentActivityResponseDTO;
import com.neo_educ.backend.apps.english.studentActivity.entity.StudentActivityEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentActivityMapper {

    StudentActivityEntity toEntity(StudentActivityCreateDTO studentActivityCreateDTO);

    StudentActivityResponseDTO toDTO(StudentActivityEntity studentActivityEntity);
}

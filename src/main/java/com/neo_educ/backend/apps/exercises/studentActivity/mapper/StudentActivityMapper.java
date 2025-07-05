package com.neo_educ.backend.apps.exercises.studentActivity.mapper;

import com.neo_educ.backend.apps.exercises.studentActivity.dto.StudentActivityCreateDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.dto.StudentActivityResponseDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.entity.StudentActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentActivityMapper {

    StudentActivityEntity toEntity(StudentActivityCreateDTO studentActivityCreateDTO);

    StudentActivityResponseDTO toDTO(StudentActivityEntity studentActivityEntity);
}

package com.neo_educ.backend.modules.studentActivity.mapper;

import com.neo_educ.backend.modules.studentActivity.dto.StudentActivityCreateDTO;
import com.neo_educ.backend.modules.studentActivity.dto.StudentActivityResponseDTO;
import com.neo_educ.backend.modules.studentActivity.entity.StudentActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentActivityMapper {

    StudentActivityEntity toEntity(StudentActivityCreateDTO studentActivityCreateDTO);

    StudentActivityResponseDTO toDTO(StudentActivityEntity studentActivityEntity);
}

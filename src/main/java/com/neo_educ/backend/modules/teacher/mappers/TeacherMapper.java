package com.neo_educ.backend.modules.teacher.mappers;

import com.neo_educ.backend.modules.teacher.dto.TeacherResponseDTO;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    TeacherResponseDTO toDTO(TeacherEntity teacherEntity);
}

package com.neo_educ.backend.mappers;

import com.neo_educ.backend.dto.auth.TeacherDTO;
import com.neo_educ.backend.model.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    TeacherDTO toDTO(TeacherEntity teacherEntity);
}

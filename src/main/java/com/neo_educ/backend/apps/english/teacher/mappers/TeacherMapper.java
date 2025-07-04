package com.neo_educ.backend.apps.english.teacher.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.neo_educ.backend.apps.english.auth.dto.RegisterDTO;
import com.neo_educ.backend.apps.english.teacher.dto.TeacherResponseDTO;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    TeacherResponseDTO toDTO(TeacherEntity teacherEntity);

    TeacherEntity toEntity(RegisterDTO registerDTO);
}

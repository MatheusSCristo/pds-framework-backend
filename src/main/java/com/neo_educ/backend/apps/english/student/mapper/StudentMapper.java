package com.neo_educ.backend.apps.english.student.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.neo_educ.backend.apps.english.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.entity.StudentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    StudentEntity toEntity(StudentRegisterDTO studentRegisterDTO);

    StudentResponseDTO toResponseDTO(StudentEntity studentEntity);

}

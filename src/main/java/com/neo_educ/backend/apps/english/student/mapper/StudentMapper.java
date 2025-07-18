package com.neo_educ.backend.apps.english.student.mapper;

import com.neo_educ.backend.apps.english.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.core.mapper.ClientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper extends ClientMapper<StudentRegisterDTO, StudentResponseDTO, StudentEntity> {

    @Override
    StudentEntity toEntity(StudentRegisterDTO createDto);

    @Mapping(target = "teacherEmail", source = "owner.email")
    @Override
    StudentResponseDTO toResponse(StudentEntity entity);
}
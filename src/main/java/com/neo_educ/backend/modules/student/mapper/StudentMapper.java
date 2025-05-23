package com.neo_educ.backend.modules.student.mapper;

import com.neo_educ.backend.modules.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.modules.student.dto.StudentRequestDTO;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    StudentEntity toEntity(StudentRegisterDTO studentRegisterDTO);

    StudentResponseDTO toResponseDTO(StudentEntity studentEntity);

}

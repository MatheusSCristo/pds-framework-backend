package com.neo_educ.backend.modules.classplans.mappers;

import com.neo_educ.backend.modules.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassPlansMapper {

    ClassPlansResponseDTO toResponse(ClassPlansEntity entity);


}

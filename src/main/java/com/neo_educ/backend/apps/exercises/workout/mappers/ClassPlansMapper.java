package com.neo_educ.backend.apps.exercises.workout.mappers;

import com.neo_educ.backend.apps.exercises.workout.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.apps.exercises.workout.entity.ClassPlansEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassPlansMapper {

    ClassPlansResponseDTO toResponse(ClassPlansEntity entity);

}
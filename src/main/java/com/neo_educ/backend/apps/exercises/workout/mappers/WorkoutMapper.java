package com.neo_educ.backend.apps.exercises.workout.mappers;

import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutResponseDTO;
import com.neo_educ.backend.apps.exercises.workout.entity.WorkoutEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkoutMapper {

    WorkoutResponseDTO toResponse(WorkoutEntity entity);

}
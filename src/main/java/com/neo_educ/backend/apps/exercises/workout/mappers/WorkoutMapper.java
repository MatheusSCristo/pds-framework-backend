package com.neo_educ.backend.apps.exercises.workout.mappers;

import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutCreateDTO;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutResponseDTO;
import com.neo_educ.backend.apps.exercises.workout.entity.WorkoutEntity;
import com.neo_educ.backend.core.mapper.SessionMapper; // Importa a interface base
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkoutMapper extends SessionMapper<WorkoutCreateDTO, WorkoutResponseDTO, WorkoutEntity> {
    @Override
    WorkoutEntity toEntity(WorkoutCreateDTO createDto);

    @Override
    WorkoutResponseDTO toResponse(WorkoutEntity entity);
}
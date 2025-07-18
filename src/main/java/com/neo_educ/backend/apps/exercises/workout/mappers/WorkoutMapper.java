package com.neo_educ.backend.apps.exercises.workout.mappers;

import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutCreateDTO;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutResponseDTO;
import com.neo_educ.backend.apps.exercises.workout.entity.WorkoutEntity;
import com.neo_educ.backend.core.mapper.SessionMapper; // Importa a interface base
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutMapper extends SessionMapper<WorkoutCreateDTO, WorkoutResponseDTO, WorkoutEntity> {
    
    @Mapping(target = "inputData", source = "topic")
    @Mapping(target = "aiGeneratedContent", source = "content") 
    WorkoutResponseDTO toResponse(WorkoutEntity entity);


    @Mapping(target = "date", source = "classDate")
    @Mapping(target = "topic", source = "inputData")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "instructions", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorkoutEntity toEntity(WorkoutCreateDTO dto);
}
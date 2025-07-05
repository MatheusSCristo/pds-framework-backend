package com.neo_educ.backend.apps.exercises.studentActivity.mapper;

import com.neo_educ.backend.apps.exercises.studentActivity.dto.AthleteActivityCreateDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.dto.AthleteActivityResponseDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.entity.AthleteActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AthleteActivityMapper {

    AthleteActivityEntity toEntity(AthleteActivityCreateDTO athleteActivityCreateDTO);

    AthleteActivityResponseDTO toDTO(AthleteActivityEntity athleteActivityEntity);
}

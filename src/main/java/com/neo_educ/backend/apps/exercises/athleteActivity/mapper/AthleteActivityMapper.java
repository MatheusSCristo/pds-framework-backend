package com.neo_educ.backend.apps.exercises.athleteActivity.mapper;

import com.neo_educ.backend.apps.exercises.athleteActivity.dto.AthleteActivityCreateDTO;
import com.neo_educ.backend.apps.exercises.athleteActivity.dto.AthleteActivityResponseDTO;
import com.neo_educ.backend.apps.exercises.athleteActivity.entity.AthleteActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AthleteActivityMapper {

    AthleteActivityEntity toEntity(AthleteActivityCreateDTO athleteActivityCreateDTO);

    AthleteActivityResponseDTO toDTO(AthleteActivityEntity athleteActivityEntity);
}

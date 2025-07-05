package com.neo_educ.backend.apps.exercises.athlete.mapper;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteRegisterDTO;
import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AthleteMapper {

    AthleteEntity toEntity(AthleteRegisterDTO athleteRegisterDTO);

    AthleteResponseDTO toResponseDTO(AthleteEntity studentEntity);

}

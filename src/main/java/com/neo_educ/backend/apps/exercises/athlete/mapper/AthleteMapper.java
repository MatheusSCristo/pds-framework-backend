package com.neo_educ.backend.apps.exercises.athlete.mapper;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteRegisterDTO;
import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.core.mapper.ClientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AthleteMapper extends ClientMapper<AthleteRegisterDTO, AthleteResponseDTO, AthleteEntity> {
    
    @Override
    AthleteEntity toEntity(AthleteRegisterDTO createDto);

    @Mapping(target = "personalEmail", source = "owner.email")
    @Override
    AthleteResponseDTO toResponse(AthleteEntity entity);
}
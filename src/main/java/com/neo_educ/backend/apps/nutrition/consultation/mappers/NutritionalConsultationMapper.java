package com.neo_educ.backend.apps.nutrition.consultation.mappers;

import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationCreateDTO;
import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationResponseDTO;
import com.neo_educ.backend.apps.nutrition.consultation.entity.NutritionalConsultationEntity;
import com.neo_educ.backend.core.mapper.SessionMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NutritionalConsultationMapper extends SessionMapper<NutritionalConsultationCreateDTO, NutritionalConsultationResponseDTO, NutritionalConsultationEntity> {

    @Override
    NutritionalConsultationEntity toEntity(NutritionalConsultationCreateDTO createDto);

    @Override
    NutritionalConsultationResponseDTO toResponse(NutritionalConsultationEntity entity);
}
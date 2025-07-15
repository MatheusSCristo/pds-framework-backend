package com.neo_educ.backend.apps.nutrition.consultation.mappers;

import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationResponseDTO;
import com.neo_educ.backend.apps.nutrition.consultation.entity.NutritionalConsultationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NutritionalConsultationMapper {


    @Mapping(source = "date", target = "consultationDate") // Mapeia o campo 'date' da entidade para 'consultationDate' no DTO
    NutritionalConsultationResponseDTO toResponse(NutritionalConsultationEntity entity);

}
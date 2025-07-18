package com.neo_educ.backend.apps.nutrition.patient.mapper;

import com.neo_educ.backend.apps.nutrition.patient.dto.PatientRegisterDTO;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientResponseDTO;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.core.mapper.ClientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper extends ClientMapper<PatientRegisterDTO, PatientResponseDTO, PatientEntity> {
    
    @Override
    PatientEntity toEntity(PatientRegisterDTO createDto);

    @Mapping(target = "nutritionistEmail", source = "owner.email")
    @Override
    PatientResponseDTO toResponse(PatientEntity entity);
}
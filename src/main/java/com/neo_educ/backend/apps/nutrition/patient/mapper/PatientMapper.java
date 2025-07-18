package com.neo_educ.backend.apps.nutrition.patient.mapper;

import com.neo_educ.backend.apps.nutrition.patient.dto.PatientRegisterDTO;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientResponseDTO;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.core.mapper.ClientMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper extends ClientMapper<PatientRegisterDTO, PatientResponseDTO, PatientEntity> {
    
    @Override
    PatientEntity toEntity(PatientRegisterDTO createDto);

    @Override
    PatientResponseDTO toResponse(PatientEntity entity);
}
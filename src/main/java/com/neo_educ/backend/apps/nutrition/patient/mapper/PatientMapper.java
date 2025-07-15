package com.neo_educ.backend.apps.nutrition.patient.mapper;

import com.neo_educ.backend.apps.nutrition.patient.dto.PatientRegisterDTO;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientResponseDTO;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

    PatientEntity toEntity(PatientRegisterDTO patientRegisterDTO);

    @Mapping(source = "nutritionist.email", target = "nutritionistEmail")
    PatientResponseDTO toResponseDTO(PatientEntity patientEntity);

}
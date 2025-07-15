package com.neo_educ.backend.apps.nutrition.nutritionist.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.neo_educ.backend.apps.nutrition.auth.dto.NutritionistRegisterDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.dto.NutritionistResponseDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NutritionistMapper {

    NutritionistResponseDTO toDTO(NutritionistEntity nutritionistEntity);

    NutritionistEntity toEntity(NutritionistRegisterDTO registerDTO);

}
package com.neo_educ.backend.apps.nutrition.mealPlan.mapper;

import com.neo_educ.backend.apps.nutrition.mealPlan.dto.MealPlanResponseDTO;
import com.neo_educ.backend.apps.nutrition.mealPlan.entity.MealPlanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MealPlanMapper {
    @Mapping(source = "patient.id", target = "patientId")
    MealPlanResponseDTO toResponseDTO(MealPlanEntity mealPlanEntity);
}
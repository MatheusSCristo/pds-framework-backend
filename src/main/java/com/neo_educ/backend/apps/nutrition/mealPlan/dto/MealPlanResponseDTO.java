package com.neo_educ.backend.apps.nutrition.mealPlan.dto;

import java.time.LocalDate;

public record MealPlanResponseDTO(
    Long id,
    String title,
    String content,
    Long patientId,
    LocalDate startDate,
    LocalDate endDate
) {}
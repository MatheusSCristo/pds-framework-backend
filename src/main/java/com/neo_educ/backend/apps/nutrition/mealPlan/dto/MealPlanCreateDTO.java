package com.neo_educ.backend.apps.nutrition.mealPlan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MealPlanCreateDTO(
    @NotNull(message = "O ID do paciente é obrigatório.")
    Long patientId,

    @NotBlank(message = "A categoria ou foco do plano é obrigatório.")
    String category
) {}
package com.neo_educ.backend.apps.nutrition.materialGeneration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ExportMealPlanDTO(
    @NotBlank(message = "O email do paciente é obrigatório.")
    @Email
    String patientEmail,

    @NotBlank(message = "O título do plano alimentar é obrigatório.")
    String mealPlanTitle,

    @NotBlank(message = "O conteúdo do plano alimentar é obrigatório.")
    String mealPlanContent
) {}
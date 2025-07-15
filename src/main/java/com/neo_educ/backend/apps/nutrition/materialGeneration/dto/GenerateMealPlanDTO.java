package com.neo_educ.backend.apps.nutrition.materialGeneration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para solicitar a geração de um plano alimentar.
 *
 * @param patientId O ID do paciente para o qual o plano será gerado.
 * @param category A categoria ou foco principal do plano (ex: "Perda de Peso").
 */
public record GenerateMealPlanDTO(
    @NotNull(message = "O ID do paciente não pode ser nulo.")
    Long patientId,

    @NotBlank(message = "A categoria ou foco do plano não pode estar em branco.")
    String category
) {}
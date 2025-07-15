package com.neo_educ.backend.apps.nutrition.materialGeneration.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitar a geração de uma lista de compras.
 *
 * @param mealPlanText O conteúdo completo do plano alimentar que servirá de base.
 */
public record GenerateShoppingListDTO(
    @NotBlank(message = "O texto do plano alimentar não pode estar em branco.")
    String mealPlanText
) {}
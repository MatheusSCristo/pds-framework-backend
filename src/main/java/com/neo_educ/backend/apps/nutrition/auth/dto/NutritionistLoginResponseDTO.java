package com.neo_educ.backend.apps.nutrition.auth.dto;

import com.neo_educ.backend.apps.nutrition.nutritionist.dto.NutritionistResponseDTO;

public record NutritionistLoginResponseDTO(
        String token,
        long expiresIn,
        NutritionistResponseDTO user
) {
}
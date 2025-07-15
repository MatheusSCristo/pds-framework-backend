package com.neo_educ.backend.apps.nutrition.nutritionist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NutritionistResponseDTO(
        String name,
        @JsonProperty("last_name")
        String lastName,
        String email,
        String phone,
        String specialty,
        @JsonProperty("invite_token")
        String inviteToken
) {
}
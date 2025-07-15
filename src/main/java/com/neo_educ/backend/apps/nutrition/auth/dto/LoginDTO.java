package com.neo_educ.backend.apps.nutrition.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank String email,
        @NotBlank String password
){}

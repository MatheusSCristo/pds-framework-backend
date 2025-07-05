package com.neo_educ.backend.core.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank String email,
        @NotBlank String password
){}

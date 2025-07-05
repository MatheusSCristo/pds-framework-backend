package com.neo_educ.backend.core.dto.auth;

import com.neo_educ.backend.core.dto.user.UserResponseDTO;

public record LoginResponseDTO(
        String token,
        long expiresIn,
        UserResponseDTO user
) {
}

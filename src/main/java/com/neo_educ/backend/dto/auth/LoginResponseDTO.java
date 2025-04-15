package com.neo_educ.backend.dto.auth;

public record LoginResponseDTO(
        String token,
        long expiresIn,
        TeacherDTO user
) {
}

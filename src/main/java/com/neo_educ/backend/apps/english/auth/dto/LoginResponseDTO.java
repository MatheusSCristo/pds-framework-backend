package com.neo_educ.backend.apps.english.auth.dto;

import com.neo_educ.backend.apps.english.teacher.dto.TeacherResponseDTO;

public record LoginResponseDTO(
        String token,
        long expiresIn,
        TeacherResponseDTO user
) {
}

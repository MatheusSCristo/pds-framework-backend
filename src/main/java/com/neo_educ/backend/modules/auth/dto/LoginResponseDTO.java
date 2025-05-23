package com.neo_educ.backend.modules.auth.dto;

import com.neo_educ.backend.modules.teacher.dto.TeacherResponseDTO;

public record LoginResponseDTO(
        String token,
        long expiresIn,
        TeacherResponseDTO user
) {
}

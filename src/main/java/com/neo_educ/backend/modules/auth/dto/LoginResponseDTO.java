package com.neo_educ.backend.modules.auth.dto;

import com.neo_educ.backend.modules.teacher.dto.TeacherDTO;

public record LoginResponseDTO(
        String token,
        long expiresIn,
        TeacherDTO user
) {
}

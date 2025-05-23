package com.neo_educ.backend.modules.studentActivity.dto;

import com.neo_educ.backend.modules.studentActivity.enums.StudentActivityStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentActivityCreateDTO(
        @NotNull Integer unit,
        @NotNull Integer grade,
        @NotNull StudentActivityStatus status,
        @NotBlank String subject,
        @NotNull Long studentId
) {
}

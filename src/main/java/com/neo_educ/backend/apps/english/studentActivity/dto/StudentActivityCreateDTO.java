package com.neo_educ.backend.apps.english.studentActivity.dto;

import com.neo_educ.backend.apps.english.studentActivity.enums.StudentActivityStatus;

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

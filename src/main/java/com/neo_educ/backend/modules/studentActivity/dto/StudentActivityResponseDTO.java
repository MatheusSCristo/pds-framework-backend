package com.neo_educ.backend.modules.studentActivity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo_educ.backend.modules.studentActivity.enums.StudentActivityStatus;

import java.time.LocalDateTime;

public record StudentActivityResponseDTO(
        Long id,
        Integer unit,
        Integer grade,
        StudentActivityStatus status,
        String subject,
        @JsonProperty("date")
        LocalDateTime createdAt
) {
}

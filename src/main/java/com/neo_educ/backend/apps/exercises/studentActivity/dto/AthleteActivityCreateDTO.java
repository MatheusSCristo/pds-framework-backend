package com.neo_educ.backend.apps.exercises.studentActivity.dto;

import jakarta.validation.constraints.NotNull;

public record AthleteActivityCreateDTO(
        @NotNull String activityDescription,
        @NotNull String activityType,
        @NotNull Double performanceMetricValue,
        @NotNull String performanceMetricUnit,
        @NotNull Long studentId
) {
}

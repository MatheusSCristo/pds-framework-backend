package com.neo_educ.backend.apps.exercises.athleteActivity.dto;

import jakarta.validation.constraints.NotNull;

public record AthleteActivityCreateDTO(
        @NotNull String activityDescription,
        @NotNull String activityType,
        @NotNull Double performanceMetricValue,
        @NotNull String performanceMetricUnit,
        @NotNull Long athleteId
) {
}

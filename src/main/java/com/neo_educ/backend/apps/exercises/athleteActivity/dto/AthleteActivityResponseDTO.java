package com.neo_educ.backend.apps.exercises.athleteActivity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AthleteActivityResponseDTO(
        Long id,
        String activityDescription,
        String activityType,
        Double performanceMetricValue,
        String performanceMetricUnit,
        @JsonProperty("date")
        LocalDateTime createdAt
) {
}

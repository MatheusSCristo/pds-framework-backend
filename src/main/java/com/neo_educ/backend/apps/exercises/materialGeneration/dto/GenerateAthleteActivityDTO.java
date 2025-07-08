package com.neo_educ.backend.apps.exercises.materialGeneration.dto;

import com.neo_educ.backend.apps.exercises.athlete.enums.WorkoutLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenerateAthleteActivityDTO(
        @NotNull
        Long athleteId,
        @NotBlank
        String topic,
        WorkoutLevel workoutLevel,
        Boolean interests,
        Boolean level

) {
}

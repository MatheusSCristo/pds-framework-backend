package com.neo_educ.backend.apps.exercises.materialGeneration.dto;

import com.neo_educ.backend.apps.exercises.athlete.enums.WorkoutLevel;

import java.util.List;

public record GenerateMaterialDTO(String topic, WorkoutLevel level, List<String> goals) {
}

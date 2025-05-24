package com.neo_educ.backend.modules.materialGeneration.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ExportExerciseDTO(
        @NotBlank
        List<String> selectedExercises,

        @NotBlank
        String studentEmail
) {
}

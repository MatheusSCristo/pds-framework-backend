package com.neo_educ.backend.modules.materialGeneration.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GenerateExerciseDTO(
        @NotBlank
        String topic,

        @NotBlank
        String level,

        List<String> interests,

        @NotNull
        @Min(1)
        @Max(20)
        int quantity
) {
}

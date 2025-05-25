package com.neo_educ.backend.modules.materialGeneration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ExportExerciseDTO(
        @NotNull(message = "A lista de exercícios não pode ser nula")
        @NotEmpty(message = "A lista de exercícios não pode estar vazia")
        List<String> selectedExercises,

        @NotBlank(message = "O campo [email] não pode ser vazio")
        String studentEmail
) {
}

package com.neo_educ.backend.apps.exercises.materialGeneration.dto;

import com.neo_educ.backend.apps.english.student.enums.ProficiencyLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenerateStudentActivityDTO(
        @NotNull
        Long studentId,
        @NotBlank
        String subject,
        ProficiencyLevel setLevel,
        Boolean interests,
        Boolean level

) {
}

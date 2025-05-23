package com.neo_educ.backend.modules.materialGeneration.dto;

import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;
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

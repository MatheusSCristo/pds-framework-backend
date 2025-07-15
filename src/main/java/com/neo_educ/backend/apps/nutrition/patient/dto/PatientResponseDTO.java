package com.neo_educ.backend.apps.nutrition.patient.dto;



import java.time.LocalDateTime;
import java.util.Set;

import com.neo_educ.backend.apps.nutrition.patient.enums.NutritionalGoal;

public record PatientResponseDTO(
    Long id,
    String name,
    String email,
    String allergies,
    String anthropometricData,
    Set<NutritionalGoal> nutritionalGoals,
    String nutritionistEmail,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
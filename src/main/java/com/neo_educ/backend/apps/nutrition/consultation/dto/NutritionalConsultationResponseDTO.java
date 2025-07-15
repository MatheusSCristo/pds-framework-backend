package com.neo_educ.backend.apps.nutrition.consultation.dto;

import java.time.LocalDateTime;

public record NutritionalConsultationResponseDTO(
  Long id,
  String title,
  LocalDateTime consultationDate,
  String consultationType
) {}
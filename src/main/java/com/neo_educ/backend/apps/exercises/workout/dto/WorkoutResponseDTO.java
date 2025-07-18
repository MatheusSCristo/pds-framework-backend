package com.neo_educ.backend.apps.exercises.workout.dto;

import java.time.LocalDateTime;


public record WorkoutResponseDTO(
  Long id,
  String title,
  LocalDateTime date,
  String inputData,
  String aiGeneratedContent
) {} 


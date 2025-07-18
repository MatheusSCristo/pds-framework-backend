package com.neo_educ.backend.apps.exercises.workout.dto;

import java.time.LocalDateTime;

import com.neo_educ.backend.apps.exercises.workout.enums.WorkoutGoal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkoutCreateDTO(
  @NotBlank(message = "O tópico não pode estar em branco")
  String title,

  @NotNull(message = "A data da aula é obrigatória")
  LocalDateTime classDate,

  @NotBlank(message = "O conteúdo da aula não pode estar em branco")
  String inputData,

  @NotNull(message = "O objetivo do treino é obrigatório")
  WorkoutGoal goal,

  @NotBlank(message = "A duração deve ser especificada")
  String duration,

  @NotNull(message = "O número de treinos por semana é obrigatório")
  @Min(value = 1, message = "Deve haver pelo menos 1 treino por semana")
  Integer workoutsPerWeek
) {} 
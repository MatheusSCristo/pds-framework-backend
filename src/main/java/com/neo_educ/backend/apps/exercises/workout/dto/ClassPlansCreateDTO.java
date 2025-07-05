package com.neo_educ.backend.apps.exercises.workout.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClassPlansCreateDTO(
  @NotBlank(message = "O tópico não pode estar em branco")
  String title,

  @NotNull(message = "A data da aula é obrigatória")
  LocalDateTime classDate,

  @NotBlank(message = "O conteúdo da aula não pode estar em branco")
  String inputData
) {} 
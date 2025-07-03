package com.neo_educ.backend.apps.english.classplans.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClassPlansUpdateDTO(
  @NotBlank(message = "O tópico não pode estar em branco")
  String topic,

  @NotNull(message = "A data da aula é obrigatória")
  LocalDateTime classDate,

  @NotBlank(message = "O conteúdo da aula não pode estar em branco")
  String inputData
) {} 
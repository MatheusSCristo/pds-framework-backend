package com.neo_educ.backend.apps.nutrition.consultation.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NutritionalConsultationCreateDTO(

  @NotBlank(message = "O título da consulta não pode estar em branco")
  String title,

  @NotBlank(message = "O tipo da consulta não pode estar em branco")
  String consultationType,

  @NotNull(message = "A data da consulta é obrigatória")
  LocalDateTime consultationDate

) {}
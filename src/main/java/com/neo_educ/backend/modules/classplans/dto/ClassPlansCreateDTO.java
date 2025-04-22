package com.neo_educ.backend.modules.classplans.dto;

import java.time.LocalDateTime;

public record ClassPlansCreateDTO(
  String topic,
  LocalDateTime classDate,
  String inputData,
  String teacher_email
) {} 
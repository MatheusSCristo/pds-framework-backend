package com.neo_educ.backend.dto.class_plans;

import java.time.LocalDateTime;

public record ClassPlansUpdateDTO(
  String topic,
  LocalDateTime classDate,
  String inputData
) {} 
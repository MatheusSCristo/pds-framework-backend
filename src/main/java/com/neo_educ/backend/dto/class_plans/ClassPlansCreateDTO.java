package com.neo_educ.backend.dto.class_plans;

import java.time.LocalDateTime;

public record ClassPlansCreateDTO(
  String topic,
  LocalDateTime classDate,
  String inputData,
  Long teacher_id
) {} 
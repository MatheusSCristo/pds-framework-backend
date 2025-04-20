package com.neo_educ.backend.dto.class_plans;

import java.time.LocalDateTime;

import com.neo_educ.backend.enums.ClassPlanStatus;

public record ClassPlansFindByIdDTO (
  String topic,
  LocalDateTime classDate,
  String inputData,
  String aiGeneratedContent,
  ClassPlanStatus status
) {} 


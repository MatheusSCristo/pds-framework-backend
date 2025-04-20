package com.neo_educ.backend.dto.class_plans;

import java.time.LocalDateTime;

import com.neo_educ.backend.enums.ClassPlanStatus;

public record ClassPlansOutputDTO (
  String topic,
  LocalDateTime classDate,
  String inputData,
  String aiGeneratedContent,
  ClassPlanStatus status
) {} 


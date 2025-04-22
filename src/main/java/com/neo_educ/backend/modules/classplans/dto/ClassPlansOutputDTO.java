package com.neo_educ.backend.modules.classplans.dto;

import java.time.LocalDateTime;

import com.neo_educ.backend.modules.classplans.enums.ClassPlanStatus;



public record ClassPlansOutputDTO (
  Long id,
  String topic,
  LocalDateTime classDate,
  String inputData,
  String aiGeneratedContent,
  ClassPlanStatus status
) {} 


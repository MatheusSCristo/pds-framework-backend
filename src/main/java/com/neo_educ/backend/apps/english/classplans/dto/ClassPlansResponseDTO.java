package com.neo_educ.backend.apps.english.classplans.dto;

import java.time.LocalDateTime;

import com.neo_educ.backend.apps.english.classplans.enums.ClassPlanStatus;



public record ClassPlansResponseDTO(
  Long id,
  String topic,
  LocalDateTime classDate,
  String inputData,
  String aiGeneratedContent,
  ClassPlanStatus status
) {} 


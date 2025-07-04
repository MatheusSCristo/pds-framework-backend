package com.neo_educ.backend.apps.english.classplans.dto;

import java.time.LocalDateTime;


public record ClassPlansResponseDTO(
  Long id,
  String title,
  LocalDateTime classDate,
  String inputData,
  String aiGeneratedContent
) {} 


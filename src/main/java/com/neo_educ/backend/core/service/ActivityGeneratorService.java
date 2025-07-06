package com.neo_educ.backend.core.service;

public interface ActivityGeneratorService {

    String generateSession(String topic);

    String generateMaterialContent(Object dto);

    String generateActivityContent(Long userId, String category);

    String generateReportContent(Object data);

    String generateExerciseContent(Object dto);
}
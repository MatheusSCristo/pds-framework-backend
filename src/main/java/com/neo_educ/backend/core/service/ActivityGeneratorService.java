package com.neo_educ.backend.core.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentReportDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GradeAverageBySubject;

import java.util.List;
public interface ActivityGeneratorService {

    String generateSession(String topic);

    String generateMaterialContent(Object dto);

    String generateActivityContent(Long userId, String category);

    String generateReportContent(Object data);

    String generateExerciseContent(Object dto);
}
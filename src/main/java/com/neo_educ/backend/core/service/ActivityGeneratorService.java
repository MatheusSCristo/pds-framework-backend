package com.neo_educ.backend.core.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GradeAverageBySubject;

import java.util.List;
public interface ActivityGeneratorService {

    String generateClassPlan(String topic);

    String generateMaterialContent(GenerateMaterialDTO dto);

    String generateStudentActivityContent(Long studentId, String subject);

    String generateStudentReportContent(List<GradeAverageBySubject> reportData);

    String generateExerciseContent(GenerateExerciseDTO dto);
}
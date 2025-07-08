// File: matheusscristo/pds-framework-backend/pds-framework-backend-refactor-migrate-english-to-framework/src/main/java/com/neo_educ/backend/apps/english/materialGeneration/service/GenerateMaterialService.java
package com.neo_educ.backend.apps.english.materialGeneration.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentActivityDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentReportDTO;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GenerateMaterialService {

    @Autowired
    @Qualifier("englishActivityService")
    private ActivityGeneratorService activityGenerator;

    public String generate(GenerateMaterialDTO generateMaterialDTO) {
        return activityGenerator.generateMaterialContent(generateMaterialDTO);
    }

    public String generateStudentActivity(GenerateStudentActivityDTO studentActivityDTO) {
        return activityGenerator.generateActivityContent(
                studentActivityDTO
        );
    }

    public String generateStudentReport(GenerateStudentReportDTO generateStudentReportDTO) {
        return activityGenerator.generateReportContent(generateStudentReportDTO);
    }

    public String generateExercise(GenerateExerciseDTO generateExerciseDTO) {
        return activityGenerator.generateExerciseContent(generateExerciseDTO);
    }
}
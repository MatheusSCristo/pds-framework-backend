// File: matheusscristo/pds-framework-backend/pds-framework-backend-refactor-migrate-english-to-framework/src/main/java/com/neo_educ/backend/apps/english/materialGeneration/service/GenerateMaterialService.java
package com.neo_educ.backend.apps.exercises.materialGeneration.service;

import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateAthleteActivityDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateAthleteReportDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonalGenerateMaterialService {

    @Autowired
    @Qualifier("exercisesActivityService")
    private ActivityGeneratorService activityGenerator;

    public String generate(GenerateMaterialDTO generateMaterialDTO) {
        return activityGenerator.generateMaterialContent(generateMaterialDTO);
    }

    public String generateAthleteActivity(GenerateAthleteActivityDTO athleteActivityDTO) {
        return activityGenerator.generateActivityContent(
                athleteActivityDTO
        );
    }

    public String generateAthleteReport(GenerateAthleteReportDTO generateAthleteReportDTO) {
        return activityGenerator.generateReportContent(generateAthleteReportDTO);
    }

    public String generateExercise(GenerateExerciseDTO generateExerciseDTO) {
        return activityGenerator.generateExerciseContent(generateExerciseDTO);
    }
}
package com.neo_educ.backend.apps.exercises.materialGeneration.service;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.service.AthleteService;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateAthleteActivityDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateAthleteReportDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.utils.PersonalSentencesPromptTemplate;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("exercisesActivityService")
public class PersonalActivityService extends ActivityGeneratorService<GenerateAthleteActivityDTO, GenerateAthleteReportDTO, GenerateMaterialDTO, GenerateExerciseDTO> {

    @Autowired
    private PersonalSentencesPromptTemplate promptTemplate;
    @Autowired
    private AthleteService athleteService;


    @Override
    protected String buildPromptForSession(String topic) {
        return promptTemplate.createWorkoutprompt(topic);
    }

    @Override
    protected String buildPromptForActivity(GenerateAthleteActivityDTO activityDto) {
        AthleteResponseDTO athlete = athleteService.findById(activityDto.athleteId());
        return promptTemplate.createActivityPrompt(activityDto, athlete);
    }

    @Override
    protected String buildPromptForReport(GenerateAthleteReportDTO reportDto) {
        return promptTemplate.createReportPrompt(reportDto.data());
    }

    @Override
    protected String buildPromptForExercise(GenerateExerciseDTO exerciseDto) {
        return promptTemplate.createExercisePrompt(exerciseDto);
    }

    @Override
    protected String buildPromptForMaterial(GenerateMaterialDTO materialDto) {
        return promptTemplate.createMaterialPrompt(materialDto);
    }
}
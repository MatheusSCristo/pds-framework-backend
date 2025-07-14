package com.neo_educ.backend.apps.exercises.materialGeneration.service;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.service.AthleteService;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateAthleteActivityDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateAthleteReportDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.exercises.materialGeneration.utils.PersonalSentencesPromptTemplate;
import com.neo_educ.backend.core.llm.service.LLMService;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("exercisesActivityService")
@AllArgsConstructor
public class PersonalActivityService implements ActivityGeneratorService {

    private LLMService llmService;
    private PersonalSentencesPromptTemplate promptTemplate;
    private AthleteService athleteService;

    @Override
    public String generateSession(String topic) {
        try {
            String prompt = promptTemplate.createWorkoutprompt(topic);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateMaterialContent(Object dto) {
        try {
            if (dto instanceof GenerateMaterialDTO materialDTO) {
                String prompt = promptTemplate.createMaterialPrompt(materialDTO);
                return llmService.chat(prompt);
            } else {
                throw new IllegalArgumentException("Tipo de DTO não suportado para generateMaterialContent no PersonalActivityService.");
            }
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateActivityContent(Object data) {
        try {
            if(data instanceof GenerateAthleteActivityDTO activityDTO) {
                AthleteResponseDTO athlete = athleteService.findById(activityDTO.athleteId());
                String prompt = promptTemplate.createActivityPrompt(activityDTO,athlete);
                return llmService.chat(prompt);
            }
            else{
                return  null;
            }
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateReportContent(Object reportData) {
        if(reportData instanceof GenerateAthleteReportDTO) {
            try {
                String prompt = promptTemplate.createReportPrompt(((GenerateAthleteReportDTO) reportData).data());
                return llmService.chat(prompt);
            } catch (Exception e) {
            throw new ActivityGenerateException();
            }
        }
        else{
            throw new ClassCastException();
        }

    }

    @Override
    public String generateExerciseContent(Object dto) {
        try {
            if (dto instanceof GenerateExerciseDTO exerciseDTO) {
                String prompt = promptTemplate.createExercisePrompt(exerciseDTO);
                return llmService.chat(prompt);
            } else {
                throw new IllegalArgumentException("Tipo de DTO não suportado para generateStructuredTasks no PersonalActivityService.");
            }
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }
}
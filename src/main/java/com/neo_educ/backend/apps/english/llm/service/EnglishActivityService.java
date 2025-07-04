package com.neo_educ.backend.apps.english.llm.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO; // Manter este import
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentReportDTO;
import com.neo_educ.backend.apps.english.materialGeneration.utils.EnglishSetencesPromptTemplate;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.service.StudentService;
import com.neo_educ.backend.core.llm.service.LLMService;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("englishActivityService")
public class EnglishActivityService implements ActivityGeneratorService {

    @Autowired
    private LLMService llmService;

    @Autowired
    private StudentService studentService; // Se ainda for necessário

    @Autowired
    private EnglishSetencesPromptTemplate promptTemplate;

    @Override
    public String generateSession(String topic) {
        try {
            String prompt = promptTemplate.createClassPlanPrompt(topic);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    // MÉTODO CORRIGIDO
    @Override
    public String generateMaterialContent(Object dto) { // O tipo do parâmetro DEVE ser Object
        try {
            // Fazer a verificação de tipo e o cast dentro do método
            if (dto instanceof GenerateMaterialDTO) {
                GenerateMaterialDTO materialDTO = (GenerateMaterialDTO) dto;
                String prompt = promptTemplate.createMaterialPrompt(materialDTO);
                return llmService.chat(prompt);
            } else {
                throw new IllegalArgumentException("Tipo de DTO não suportado para generateMaterialContent no EnglishActivityService.");
            }
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateActivityContent(Long userId, String category) {
        try {
            StudentResponseDTO student = studentService.findById(userId);
            String prompt = promptTemplate.createActivityPrompt(student.interests(), student.proficiencyLevel(), category);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateReportContent(Object reportData) {
        if(reportData instanceof GenerateStudentReportDTO) {
            try {
                String prompt = promptTemplate.createReportPrompt(((GenerateStudentReportDTO) reportData).data());
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
            if (dto instanceof GenerateExerciseDTO) {
                GenerateExerciseDTO exerciseDTO = (GenerateExerciseDTO) dto;
                String prompt = promptTemplate.createExercisePrompt(exerciseDTO);
                return llmService.chat(prompt);
            } else {
                throw new IllegalArgumentException("Tipo de DTO não suportado para generateStructuredTasks no EnglishActivityService.");
            }
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }
}
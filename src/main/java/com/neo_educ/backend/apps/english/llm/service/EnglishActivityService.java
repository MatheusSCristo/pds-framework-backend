// File: matheusscristo/pds-framework-backend/pds-framework-backend-refactor-migrate-english-to-framework/src/main/java/com/neo_educ/backend/apps/english/llm/service/EnglishActivityService.java
package com.neo_educ.backend.apps.english.llm.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GradeAverageBySubject;
import com.neo_educ.backend.apps.english.materialGeneration.utils.EnglishSetencesPromptTemplate;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.service.StudentService;
import com.neo_educ.backend.core.llm.service.LLMService;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("englishActivityService")
public class EnglishActivityService implements ActivityGeneratorService {

    @Autowired
    private LLMService llmService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EnglishSetencesPromptTemplate promptTemplate;


    @Override
    public String generateClassPlan(String topic) {
        try {
            String prompt = promptTemplate.createClassPlanPrompt(topic);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateMaterialContent(GenerateMaterialDTO dto) {
        try {
            String prompt = promptTemplate.createMaterialPrompt(dto);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateStudentActivityContent(Long studentId, String subject) {
        try {
            StudentResponseDTO student = studentService.findById(studentId);
            String prompt = promptTemplate.createActivityPrompt(student.interests(), student.proficiencyLevel(), subject);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateStudentReportContent(List<GradeAverageBySubject> reportData) {
        try {
            String prompt = promptTemplate.createReportPrompt(reportData);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generateExerciseContent(GenerateExerciseDTO dto) {
        try {
            String prompt = promptTemplate.createExercisePrompt(dto);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }
}
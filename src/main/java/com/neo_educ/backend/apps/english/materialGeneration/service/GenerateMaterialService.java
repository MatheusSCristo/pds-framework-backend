package com.neo_educ.backend.apps.english.materialGeneration.service;

import com.neo_educ.backend.apps.english.llm.service.LLMService;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentActivityDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentReportDTO;
import com.neo_educ.backend.apps.english.materialGeneration.utils.EnglishSetencesPromptTemplate;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.enums.InterestsEnum;
import com.neo_educ.backend.apps.english.student.enums.ProficiencyLevel;
import com.neo_educ.backend.apps.english.student.service.StudentService;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import com.neo_educ.backend.exceptions.generateMaterial.LevelNullException;
import com.neo_educ.backend.exceptions.generateMaterial.TopicNullException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GenerateMaterialService implements ActivityGeneratorService{

    @Autowired
    private LLMService llmService;

    @Autowired
    private StudentService studentService;


    @Autowired
    private EnglishSetencesPromptTemplate promptTemplate;

    public String generate(GenerateMaterialDTO generateMaterialDTO) {
        if (generateMaterialDTO.topic() == null) {
            throw new TopicNullException();
        }

        if (generateMaterialDTO.level() == null) {
            throw new LevelNullException();
        }

        try {
            String prompt = promptTemplate.createMaterialPrompt(generateMaterialDTO);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    public String generateStudentActivity(GenerateStudentActivityDTO studentActivityDTO) {

        List<InterestsEnum> interests = new ArrayList<>();
        ProficiencyLevel level = studentActivityDTO.setLevel();
        StudentResponseDTO student = studentService.findStudentDTO(studentActivityDTO.studentId());
        if (studentActivityDTO.interests()) {
            interests.addAll(student.interests());
        }
        if (studentActivityDTO.level()) {
            level = student.proficiencyLevel();
        }
        try {

            String prompt = promptTemplate.createActivityPrompt(interests, level, studentActivityDTO.subject());
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }

    }

    public String generateStudentReport(GenerateStudentReportDTO generateStudentReportDTO) {
        try {

            String prompt = promptTemplate.createReportPrompt(generateStudentReportDTO.data());
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    public String generateExercise(GenerateExerciseDTO generateExerciseDTO) {
        try {
            String prompt = promptTemplate.createExercisePrompt(generateExerciseDTO);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }

    @Override
    public String generate(Map<String, Object> infos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }

}

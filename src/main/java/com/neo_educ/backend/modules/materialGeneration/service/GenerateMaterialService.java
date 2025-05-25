package com.neo_educ.backend.modules.materialGeneration.service;

import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import com.neo_educ.backend.exceptions.generateMaterial.LevelNullException;
import com.neo_educ.backend.exceptions.generateMaterial.TopicNullException;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateStudentReportDTO;
import com.neo_educ.backend.modules.student.enums.InterestsEnum;
import com.neo_educ.backend.modules.llm.service.LLMService;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateStudentActivityDTO;
import com.neo_educ.backend.modules.materialGeneration.utils.EnglishSetencesPromptTemplate;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;
import com.neo_educ.backend.modules.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateMaterialService {

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

}

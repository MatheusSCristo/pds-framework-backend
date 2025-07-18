package com.neo_educ.backend.apps.english.llm.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO; // Manter este import
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentActivityDTO;
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
public class EnglishActivityService extends ActivityGeneratorService<GenerateStudentActivityDTO, GenerateStudentReportDTO, GenerateMaterialDTO, GenerateExerciseDTO>{

    @Autowired
    private StudentService studentService;

    @Autowired
    private EnglishSetencesPromptTemplate promptTemplate;

    @Override
    protected String buildPromptForSession(String topic) {
        return promptTemplate.createClassPlanPrompt(topic);
    }

    @Override
    protected String buildPromptForActivity(GenerateStudentActivityDTO data) {
        StudentResponseDTO student = studentService.findById(data.studentId());
        return promptTemplate.createActivityPrompt(student.interests(), student.proficiencyLevel(), data.subject());
    }

    @Override
    protected String buildPromptForReport(GenerateStudentReportDTO reportData) {
        return promptTemplate.createReportPrompt(reportData.data());
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
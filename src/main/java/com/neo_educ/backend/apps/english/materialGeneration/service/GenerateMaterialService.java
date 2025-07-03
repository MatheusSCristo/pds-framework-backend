package com.neo_educ.backend.apps.english.materialGeneration.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentActivityDTO;
import com.neo_educ.backend.apps.english.materialGeneration.dto.GenerateStudentReportDTO;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerateMaterialService {

    @Autowired
    @Qualifier("englishActivityService")
    private ActivityGeneratorService activityGenerator;

    public String generate(GenerateMaterialDTO generateMaterialDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("context", "GENERATE_MATERIAL");
        params.put("dto", generateMaterialDTO);

        return activityGenerator.generate(params);
    }

    public String generateStudentActivity(GenerateStudentActivityDTO studentActivityDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("context", "STUDENT_ACTIVITY");
        params.put("studentId", studentActivityDTO.studentId());
        params.put("subject", studentActivityDTO.subject());
        params.put("interests", studentActivityDTO.interests() ? null : List.of());

        return activityGenerator.generate(params);
    }

    public String generateStudentReport(GenerateStudentReportDTO generateStudentReportDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("context", "STUDENT_REPORT");
        params.put("reportData", generateStudentReportDTO.data());

        return activityGenerator.generate(params);
    }

    public String generateExercise(GenerateExerciseDTO generateExerciseDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("context", "EXERCISE");
        params.put("dto", generateExerciseDTO);

        return activityGenerator.generate(params);
    }
}
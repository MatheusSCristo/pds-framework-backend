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
    public String generate(Map<String, Object> infos) {
        String context = (String) infos.getOrDefault("context", "DEFAULT");

        try {
            String prompt;
            switch (context) {
                case "CLASS_PLAN_GENERATION":
                    String topicForPlan = (String) infos.get("topic");
                    prompt = promptTemplate.createClassPlanPrompt(topicForPlan);
                    break;
                case "GENERATE_MATERIAL":
                    prompt = promptTemplate.createMaterialPrompt((GenerateMaterialDTO) infos.get("dto"));
                    break;
                
                case "STUDENT_ACTIVITY":
                    StudentResponseDTO student = studentService.findStudentDTO((Long) infos.get("studentId"));
                    prompt = promptTemplate.createActivityPrompt(student.interests(), student.proficiencyLevel(), (String) infos.get("subject"));
                    break;

                case "STUDENT_REPORT":
                    List<GradeAverageBySubject> averages = (List<GradeAverageBySubject>) infos.get("reportData");
                    prompt = promptTemplate.createReportPrompt(averages);
                    break;

                case "EXERCISE":
                    prompt = promptTemplate.createExercisePrompt((GenerateExerciseDTO) infos.get("dto"));
                    break;
                
                default:
                    throw new UnsupportedOperationException("Contexto de geração não suportado: " + context);
            }

            return llmService.chat(prompt);

        } catch (Exception e) {
            throw new ActivityGenerateException();
        }
    }
}
package com.neo_educ.backend.modules.materialGeneration.service;

import com.neo_educ.backend.core.service.MaterialGeneratorServiceAbstract; // Importa a classe abstrata
import com.neo_educ.backend.exceptions.generateMaterial.ActivityGenerateException;
import com.neo_educ.backend.modules.llm.service.LLMService;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateExerciseDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GradeAverageBySubject;
import com.neo_educ.backend.modules.materialGeneration.utils.EnglishSetencesPromptTemplate;
import com.neo_educ.backend.modules.student.enums.InterestsEnum;
import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EnglishMaterialGeneratorService extends MaterialGeneratorServiceAbstract {

    private final LLMService llmService;
    private final EnglishSetencesPromptTemplate promptTemplate;

    @Autowired
    public EnglishMaterialGeneratorService(LLMService llmService, EnglishSetencesPromptTemplate promptTemplate) {
        this.llmService = llmService;
        this.promptTemplate = promptTemplate;
    }

    @Override
    public String generateMainActivity(Map<String, Object> infos) {
        if (!validateInfos(infos)) {
            throw new ActivityGenerateException();}

        try {
            String prompt;

            if (infos.containsKey("topic") && infos.containsKey("level") && infos.containsKey("quantity")) {
                GenerateExerciseDTO dto = new GenerateExerciseDTO(
                        (String) infos.get("topic"),
                        (String) infos.get("level"),
                        (List<String>) infos.getOrDefault("interests", new ArrayList<>()), // interests can be String here
                        (Integer) infos.get("quantity")
                );
                prompt = promptTemplate.createExercisePrompt(dto);
            } else if (infos.containsKey("studentId") && infos.containsKey("subject") && infos.containsKey("level")) {
                List<InterestsEnum> interestsEnums = new ArrayList<>();
                if (infos.containsKey("interests")) {
                    List<?> rawInterests = (List<?>) infos.get("interests");
                    if (rawInterests != null) {
                        for (Object item : rawInterests) {
                            if (item instanceof Integer code) {
                                interestsEnums.add(InterestsEnum.fromCode(code));
                            } else if (item instanceof InterestsEnum enumValue) {
                                interestsEnums.add(enumValue);
                            } else if (item instanceof String stringValue) {
                                // Handle if interests are passed as strings (e.g., "ENTRETERIMENTO")
                                try {
                                    interestsEnums.add(InterestsEnum.valueOf(stringValue.toUpperCase()));
                                } catch (IllegalArgumentException e) {
                                    // Log error or ignore invalid interest string
                                }
                            }
                        }
                    }
                }
                ProficiencyLevel level = (ProficiencyLevel) infos.get("level");
                String subject = (String) infos.get("subject");
                prompt = promptTemplate.createActivityPrompt(interestsEnums, level, subject);
            } else if (infos.containsKey("topic") && infos.containsKey("level") && infos.containsKey("interests") && !infos.containsKey("quantity") && !infos.containsKey("subject")) {
                GenerateMaterialDTO dto = new GenerateMaterialDTO(
                        (String) infos.get("topic"),
                        (String) infos.get("level"),
                        (List<String>) infos.get("interests")
                );
                prompt = promptTemplate.createMaterialPrompt(dto);
            } else {
                throw new IllegalArgumentException("Tipo de atividade principal de inglês desconhecido com base nos parâmetros fornecidos.");
            }

            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }

    @Override
    public String generateReport(Map<String, Object> infos) {
        if (!validateInfos(infos)) {
            throw new ActivityGenerateException();}
        try {
            List<GradeAverageBySubject> averagesBySubject = (List<GradeAverageBySubject>) infos.get("data");
            if (averagesBySubject == null) {
                throw new IllegalArgumentException("Dados de média por matéria ausentes para geração de relatório.");
            }
            String prompt = promptTemplate.createReportPrompt(averagesBySubject);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }

    @Override
    public String generateAncillaryContent(Map<String, Object> infos) {
        if (!validateInfos(infos)) {
            throw new ActivityGenerateException();}
        try {
            String contentType = (String) infos.get("contentType");
            String query = (String) infos.get("query");
            if (contentType == null || query == null) {
                throw new IllegalArgumentException("Faltando 'contentType' ou 'query' para conteúdo auxiliar.");
            }
            String prompt = promptTemplate.createAncillaryPrompt(contentType, query);
            return llmService.chat(prompt);
        } catch (Exception e) {
            throw new ActivityGenerateException();}
    }
}
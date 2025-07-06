package com.neo_educ.backend.apps.nutrition.llm.service;

import com.neo_educ.backend.apps.nutrition.entity.NutritionalReport;
import com.neo_educ.backend.apps.nutrition.entity.PatientEntity;
import com.neo_educ.backend.apps.nutrition.llm.utils.NutritionPromptTemplate;
import com.neo_educ.backend.core.llm.service.LLMService;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NutritionAIService implements ActivityGeneratorService {

    @Autowired
    private LLMService llmService;

    @Autowired
    private NutritionPromptTemplate promptTemplate;

    /**
     * Gera um plano alimentar a partir do tópico (simulado como objetivo do paciente).
     */
    @Override
    public String generateSession(String topic) {
        Map<String, String> patientData = new HashMap<>();
        patientData.put("nutritionalGoals", topic);
        patientData.put("allergies", "Não informado");
        patientData.put("anthropometricData", "Não informado");
        patientData.put("medicalHistory", "Não informado");

        String prompt = promptTemplate.createMealPlanPrompt(patientData);
        return llmService.chat(prompt);
    }

    /**
     * Gera uma lista de compras com base no conteúdo de um plano alimentar.
     */
    @Override
    public String generateMaterialContent(Object dto) {
        if (dto instanceof String mealPlanText) {
            String prompt = promptTemplate.createShoppingListPrompt(mealPlanText);
            return llmService.chat(prompt);
        }
        throw new IllegalArgumentException("O DTO precisa ser uma String representando o plano alimentar.");
    }

    /**
     * Gera uma análise nutricional a partir de um paciente e dados fictícios de consulta.
     */
    @Override
    public String generateActivityContent(Long userId, String category) {
        // Simulação de dados do paciente para geração da análise
        Map<String, String> patientData = new HashMap<>();
        patientData.put("Nome", "Paciente #" + userId);
        patientData.put("Objetivo", category);
        patientData.put("Peso", "70kg");
        patientData.put("Altura", "1.70m");

        Map<String, String> consultationData = new HashMap<>();
        consultationData.put("Tipo de Consulta", "Retorno");
        consultationData.put("Avaliação Antropométrica", "IMC dentro da faixa normal");

        String prompt = promptTemplate.createNutritionalAnalysisPrompt(patientData, consultationData);
        return llmService.chat(prompt);
    }

    /**
     * Gera um relatório nutricional baseado na entidade NutritionalReport.
     */
    @Override
    public String generateReportContent(Object data) {
        if (data instanceof NutritionalReport report) {
            Map<String, String> reportData = (Map<String, String>) report.generateReport();

            Map<String, String> patientData = new HashMap<>();
            patientData.put("weightMeasuresProgress", reportData.getOrDefault("weightMeasuresProgress", "Não informado"));
            patientData.put("planAdherence", reportData.getOrDefault("planAdherence", "Não informado"));

            Map<String, String> consultationData = new HashMap<>();
            consultationData.put("nutritionalAnalysis", reportData.getOrDefault("nutritionalAnalysis", "Não informado"));

            String prompt = promptTemplate.createNutritionalAnalysisPrompt(patientData, consultationData);
            return llmService.chat(prompt);
        }
        throw new IllegalArgumentException("O objeto fornecido não é um NutritionalReport válido.");
    }

    /**
     * Gera um plano alimentar a partir dos dados de um paciente.
     */
    @Override
    public String generateExerciseContent(Object dto) {
        if (dto instanceof PatientEntity patient) {
            Map<String, String> patientData = new HashMap<>();
            patientData.put("nutritionalGoals", patient.getNutritionalGoals());
            patientData.put("allergies", patient.getAllergies());
            patientData.put("anthropometricData", patient.getAnthropometricData());
            patientData.put("medicalHistory", patient.getMedicalHistory());

            String prompt = promptTemplate.createMealPlanPrompt(patientData);
            return llmService.chat(prompt);
        }
        throw new IllegalArgumentException("O objeto fornecido não é um PatientEntity válido.");
    }
}

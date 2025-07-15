package com.neo_educ.backend.apps.nutrition.llm.service;

import com.neo_educ.backend.apps.nutrition.entity.NutritionalReport;
import com.neo_educ.backend.apps.nutrition.llm.utils.NutritionPromptTemplate;
import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.GenerateMealPlanDTO;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.apps.nutrition.patient.repository.PatientRepository;
import com.neo_educ.backend.core.llm.service.LLMService;
import com.neo_educ.backend.core.service.ActivityGeneratorService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NutritionActivityService implements ActivityGeneratorService {

    @Autowired
    private LLMService llmService;

    @Autowired
    private NutritionPromptTemplate promptTemplate;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Gera um plano alimentar a partir do tópico (simulado como objetivo do paciente).
     */
    @Override
    public String generateSession(String topic) {
        Map<String, String> patientData = new HashMap<>();
        patientData.put("nutritionalGoals", topic);
        patientData.put("allergies", "Não informado");
        patientData.put("anthropometricData", "Não informado");

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
     * Gera um plano alimentar a partir de um objeto de dados genérico.
     * O método verifica se o objeto é um DTO ou a própria entidade do paciente.
     *
     * @param data O objeto contendo os dados. Pode ser um GenerateMealPlanDTO ou uma PatientEntity.
     * @return Uma String com o conteúdo do plano alimentar gerado pela IA.
     */
    @Override
    public String generateActivityContent(Object data) {
        PatientEntity patient;
        String category = null;

        // 1. Verifica o tipo do objeto recebido
        if (data instanceof GenerateMealPlanDTO dto) {
            // Se for um DTO, busca o paciente no banco e pega a categoria
            patient = patientRepository.findById(dto.patientId())
                    .orElseThrow(() -> new EntityNotFoundException("Paciente com ID " + dto.patientId() + " não encontrado."));
            category = dto.category();

        } else if (data instanceof PatientEntity p) {
            // Se for a própria entidade, usa ela diretamente
            patient = p;

        } else {
            // Se for um tipo inesperado, lança um erro
            throw new IllegalArgumentException("O dado fornecido deve ser do tipo GenerateMealPlanDTO ou PatientEntity.");
        }

        // 2. Chama o método auxiliar para gerar o plano
        return generateMealPlanFromPatient(patient, category);
    }

    /**
     * Método auxiliar privado que contém a lógica de geração do prompt
     * a partir de uma entidade de paciente.
     */
    private String generateMealPlanFromPatient(PatientEntity patient, String category) {
        Map<String, String> patientData = new HashMap<>();

        String goals = patient.getNutritionalGoals().stream()
                .map(goal -> goal.getDescricao())
                .collect(Collectors.joining(", "));

        patientData.put("nutritionalGoals", goals);
        patientData.put("allergies", patient.getAllergies());
        patientData.put("anthropometricData", patient.getAnthropometricData());

        if (category != null && !category.isBlank()) {
            patientData.put("mainFocus", category);
        }

        String prompt = promptTemplate.createMealPlanPrompt(patientData);
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
            patientData.put("allergies", patient.getAllergies());
            patientData.put("anthropometricData", patient.getAnthropometricData());

            String prompt = promptTemplate.createMealPlanPrompt(patientData);
            return llmService.chat(prompt);
        }
        throw new IllegalArgumentException("O objeto fornecido não é um PatientEntity válido.");
    }
}

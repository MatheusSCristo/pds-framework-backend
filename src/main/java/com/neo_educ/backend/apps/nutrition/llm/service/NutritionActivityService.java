package com.neo_educ.backend.apps.nutrition.llm.service;

import com.neo_educ.backend.apps.nutrition.entity.NutritionalReport;
import com.neo_educ.backend.apps.nutrition.llm.utils.NutritionPromptTemplate;
import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.GenerateMealPlanDTO;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.apps.nutrition.patient.repository.PatientRepository;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service("nutritionActivityService")
public class NutritionActivityService extends ActivityGeneratorService<Object, Object, String, Object> {

    @Autowired
    private NutritionPromptTemplate promptTemplate;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    protected String buildPromptForSession(String topic) {
        // Gera um plano alimentar genérico baseado em um tópico/objetivo
        Map<String, String> patientData = new HashMap<>();
        patientData.put("nutritionalGoals", topic);
        patientData.put("allergies", "Não informado");
        patientData.put("anthropometricData", "Não informado");
        return promptTemplate.createMealPlanPrompt(patientData);
    }

    @Override
    protected String buildPromptForActivity(Object data) {
        PatientEntity patient;
        String category = null;

        if (data instanceof GenerateMealPlanDTO dto) {
            patient = patientRepository.findById(dto.patientId())
                    .orElseThrow(() -> new EntityNotFoundException("Paciente com ID " + dto.patientId() + " não encontrado."));
            category = dto.category();
        } else if (data instanceof PatientEntity p) {
            patient = p;
        } else {
            throw new IllegalArgumentException("O dado fornecido deve ser do tipo GenerateMealPlanDTO ou PatientEntity.");
        }

        return generateMealPlanFromPatient(patient, category);
    }

    @Override
    protected String buildPromptForReport(Object data) {
        if (data instanceof NutritionalReport report) {
            Map<String, String> reportData = (Map<String, String>) report.generateReport();
            Map<String, String> patientData = new HashMap<>();
            patientData.put("weightMeasuresProgress", reportData.getOrDefault("weightMeasuresProgress", "Não informado"));
            patientData.put("planAdherence", reportData.getOrDefault("planAdherence", "Não informado"));
            Map<String, String> consultationData = new HashMap<>();
            consultationData.put("nutritionalAnalysis", reportData.getOrDefault("nutritionalAnalysis", "Não informado"));
            return promptTemplate.createNutritionalAnalysisPrompt(patientData, consultationData);
        }
        throw new IllegalArgumentException("O objeto fornecido não é um NutritionalReport válido.");
    }

    @Override
    protected String buildPromptForExercise(Object dto) {
        // Para Nutrição, "Exercise" é a geração de um plano alimentar
        if (dto instanceof PatientEntity patient) {
            Map<String, String> patientData = new HashMap<>();
            patientData.put("allergies", patient.getAllergies());
            patientData.put("anthropometricData", patient.getAnthropometricData());
            return promptTemplate.createMealPlanPrompt(patientData);
        }
        throw new IllegalArgumentException("O objeto fornecido não é um PatientEntity válido.");
    }

    @Override
    protected String buildPromptForMaterial(String mealPlanText) {
        // Para Nutrição, "Material" é uma lista de compras
        return promptTemplate.createShoppingListPrompt(mealPlanText);
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
        return promptTemplate.createMealPlanPrompt(patientData);
    }
}
package com.neo_educ.backend.apps.nutrition.materialGeneration.service;

import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.GenerateMealPlanDTO;
import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.GeneratePatientReportDTO;
import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.GenerateShoppingListDTO;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GenerateNutritionMaterialService {

    @Autowired
    @Qualifier("nutritionActivityService") // Utiliza o serviço de IA específico para nutrição
    private ActivityGeneratorService activityGenerator;


    public String generateMealPlan(GenerateMealPlanDTO mealPlanDTO) {
        return activityGenerator.generateActivityContent(
                mealPlanDTO.patientId(),
                mealPlanDTO.category()
        );
    }

    public String generatePatientReport(GeneratePatientReportDTO reportDTO) {
        return activityGenerator.generateReportContent(reportDTO);
    }


    public String generateShoppingList(GenerateShoppingListDTO shoppingListDTO) {
        return activityGenerator.generateMaterialContent(shoppingListDTO.mealPlanText());
    }
}
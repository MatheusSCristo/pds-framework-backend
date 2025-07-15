package com.neo_educ.backend.apps.nutrition.materialGeneration.controllers;

import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.*;
import com.neo_educ.backend.apps.nutrition.materialGeneration.service.GenerateNutritionMaterialService;
import com.neo_educ.backend.apps.nutrition.materialGeneration.service.NutritionExportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nutritionist/materials")
public class NutritionMaterialGenerationController {

    @Autowired
    private GenerateNutritionMaterialService generateNutritionMaterialService;

    @Autowired
    private NutritionExportService nutritionExportService;

    @PostMapping("/meal-plan")
    public ResponseEntity<String> generateMealPlan(@Valid @RequestBody GenerateMealPlanDTO mealPlanDTO) {
        String result = generateNutritionMaterialService.generateMealPlan(mealPlanDTO);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/report")
    public ResponseEntity<String> generatePatientReport(@Valid @RequestBody GeneratePatientReportDTO reportDTO) {
        String result = generateNutritionMaterialService.generatePatientReport(reportDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/shopping-list")
    public ResponseEntity<String> generateShoppingList(@Valid @RequestBody GenerateShoppingListDTO shoppingListDTO) {
        String result = generateNutritionMaterialService.generateShoppingList(shoppingListDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/meal-plan/export")
    public ResponseEntity<String> exportMealPlan(@Valid @RequestBody ExportMealPlanDTO exportDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nutritionistEmail = authentication.getName();

        boolean success = nutritionExportService.exportMealPlan(exportDTO, nutritionistEmail);

        if (success) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(500).body("Falha ao gerar ou enviar o plano alimentar.");
    }
}
package com.neo_educ.backend.apps.nutrition.mealPlan.controller;

import com.neo_educ.backend.apps.nutrition.mealPlan.dto.MealPlanCreateDTO;
import com.neo_educ.backend.apps.nutrition.mealPlan.dto.MealPlanResponseDTO;
import com.neo_educ.backend.apps.nutrition.mealPlan.service.MealPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meal-plans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    @PostMapping
    public ResponseEntity<MealPlanResponseDTO> createMealPlan(@Valid @RequestBody MealPlanCreateDTO createDTO) {
        MealPlanResponseDTO response = mealPlanService.createMealPlan(createDTO.patientId(), createDTO.category());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<MealPlanResponseDTO> findPlanById(@PathVariable Long planId) {
        MealPlanResponseDTO response = mealPlanService.findPlanById(planId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MealPlanResponseDTO>> findAllPlansByPatient(@PathVariable Long patientId) {
        List<MealPlanResponseDTO> response = mealPlanService.findAllPlansByPatient(patientId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        mealPlanService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
// src/main/java/com/neo_educ/backend/modules/materialGeneration/controllers/MaterialGenerationController.java
package com.neo_educ.backend.modules.materialGeneration.controllers;

import com.neo_educ.backend.modules.materialGeneration.dto.*;
import com.neo_educ.backend.modules.materialGeneration.service.ExportExerciseService;
import com.neo_educ.backend.modules.materialGeneration.service.MaterialGeneratorService; // Usa o nome correto do serviço
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/materiais")
public class MaterialGenerationController {

    @Autowired
    private MaterialGeneratorService materialGeneratorService; // Injeta o serviço unificado

    @Autowired
    private ExportExerciseService exportExerciseService;

    @PostMapping("/")
    public ResponseEntity<Object> generateMaterial(@RequestBody GenerateMaterialDTO generateMaterialDTO) {
        String result = materialGeneratorService.generateMaterial(generateMaterialDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/activity")
    public ResponseEntity<Object> generateStudentActivity(@RequestBody GenerateStudentActivityDTO studentActivityDTO) {
        String result = materialGeneratorService.generateStudentActivity(studentActivityDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/report")
    public ResponseEntity<Object> generateStudentReport(@RequestBody GenerateStudentReportDTO generateStudentReportDTO) {
        String result = materialGeneratorService.generateStudentReport(generateStudentReportDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/exercise")
    public ResponseEntity<Object> generateExercises(@RequestBody @Valid GenerateExerciseDTO generateExerciseDTO) {
        String result = materialGeneratorService.generateExercise(generateExerciseDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/ancillary-content") // Novo endpoint para conteúdo auxiliar
    public ResponseEntity<Object> generateAncillaryContent(@RequestBody Map<String, Object> params) {
        String result = materialGeneratorService.generateAncillaryContent(params);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("exercise/export")
    public ResponseEntity<Object> exportExercise(@RequestBody @Valid ExportExerciseDTO exportExerciseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherEmail = authentication.getName();

        Boolean success = exportExerciseService.export(exportExerciseDTO, teacherEmail);

        if(success) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(500).body("Falha ao gerar ou enviar o PDF.");
    }

}
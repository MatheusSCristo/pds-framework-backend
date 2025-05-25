package com.neo_educ.backend.modules.materialGeneration.controllers;

import com.neo_educ.backend.modules.materialGeneration.dto.*;
import com.neo_educ.backend.modules.materialGeneration.service.ExportExerciseService;
import com.neo_educ.backend.modules.materialGeneration.service.GenerateMaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/materiais")
public class MaterialGenerationController {

    @Autowired
    private GenerateMaterialService generateMaterialService;

    @Autowired
    private ExportExerciseService exportExerciseService;

    @PostMapping("/")
    public ResponseEntity<Object> generate(@RequestBody GenerateMaterialDTO generateMaterialDTO) {
            String result = generateMaterialService.generate(generateMaterialDTO);
            return ResponseEntity.ok().body(result);
    }

    @PostMapping("/activity")
    public ResponseEntity<Object> generateStudentActivity(@RequestBody GenerateStudentActivityDTO studentActivityDTO) {
            String result = generateMaterialService.generateStudentActivity(studentActivityDTO);
            return ResponseEntity.ok().body(result);
    }

    @PostMapping("/report")
    public ResponseEntity<Object> generateStudentReport(@RequestBody GenerateStudentReportDTO generateStudentReportDTO) {
        String result = generateMaterialService.generateStudentReport(generateStudentReportDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/exercise")
    public ResponseEntity<Object> generateExercises(@RequestBody @Valid GenerateExerciseDTO generateExerciseDTO) {
        String result = generateMaterialService.generateExercise(generateExerciseDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("exercise/export")
    public ResponseEntity<Object> exportExercise(@RequestBody @Valid ExportExerciseDTO exportExerciseDTO) {
        Boolean sucess = exportExerciseService.export(exportExerciseDTO);

        if(sucess) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(500).body("Falha ao gerar ou enviar o PDF.");
    }

}

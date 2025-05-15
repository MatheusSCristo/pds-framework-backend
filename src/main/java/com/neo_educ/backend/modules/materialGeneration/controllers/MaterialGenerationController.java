package com.neo_educ.backend.modules.materialGeneration.controllers;

import com.neo_educ.backend.modules.materialGeneration.dto.GenerateMaterialDTO;
import com.neo_educ.backend.modules.materialGeneration.dto.GenerateStudentActivityDTO;
import com.neo_educ.backend.modules.materialGeneration.service.GenerateMaterialService;
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

    @PostMapping("/")
    public ResponseEntity<Object> generate(@RequestBody GenerateMaterialDTO generateMaterialDTO) {
        try {
            String result = generateMaterialService.generate(generateMaterialDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/activity")
    public ResponseEntity<Object> generateStudentActivity(@RequestBody GenerateStudentActivityDTO studentActivityDTO) {
        try {
            String result = generateMaterialService.generateStudentActivity(studentActivityDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

package com.neo_educ.backend.apps.nutrition.patient.controllers;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientRegisterDTO;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientResponseDTO;
import com.neo_educ.backend.apps.nutrition.patient.service.PatientService;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    @Qualifier("nutritionFactory") 
    private ApplicationFactory appFactory;

    @PostMapping("")
    public ResponseEntity<PatientResponseDTO> create(@RequestBody PatientRegisterDTO patientRequestDTO) {
        PatientService service = (PatientService) appFactory.createClientService();
        Long nutritionistId = getAuthenticatedNutritionistId();

        PatientResponseDTO newPatient = service.create(patientRequestDTO, nutritionistId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
    }
    @GetMapping("")
    public ResponseEntity<List<PatientResponseDTO>> findAllByNutritionist() {
        PatientService service = (PatientService) appFactory.createClientService();
        Long nutritionistId = getAuthenticatedNutritionistId();

        List<PatientResponseDTO> patients = service.findAll(nutritionistId);
        return ResponseEntity.ok(patients);
    }
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> findById(@PathVariable Long patientId) {
        PatientService service = (PatientService) appFactory.createClientService();

        PatientResponseDTO patient = service.findById(patientId);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> delete(@PathVariable Long patientId) {
        PatientService service = (PatientService) appFactory.createClientService();
        
        service.delete(patientId);
        return ResponseEntity.noContent().build();
    }
    
    private Long getAuthenticatedNutritionistId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Faz o cast para a entidade de nutricionista
        NutritionistEntity nutritionist = (NutritionistEntity) authentication.getPrincipal();
        return nutritionist.getId();
    }
}
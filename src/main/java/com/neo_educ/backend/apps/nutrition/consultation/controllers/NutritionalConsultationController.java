package com.neo_educ.backend.apps.nutrition.consultation.controllers;

import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationCreateDTO;
import com.neo_educ.backend.apps.nutrition.consultation.dto.NutritionalConsultationResponseDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nutritional-consultations")
public class NutritionalConsultationController {

    @Autowired
    @Qualifier("nutritionFactory")
    private ApplicationFactory appFactory;

    @PostMapping()
    public ResponseEntity<NutritionalConsultationResponseDTO> createConsultation(
            @Valid @RequestBody NutritionalConsultationCreateDTO dto) {
        SessionService< ?, NutritionalConsultationCreateDTO, NutritionalConsultationResponseDTO> service = getService();
        Long nutritionistId = getAuthenticatedNutritionistId();

        NutritionalConsultationResponseDTO response = service.create(dto, nutritionistId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutritionalConsultationResponseDTO> findById(@PathVariable Long id) {
        SessionService<?, ?, NutritionalConsultationResponseDTO> service = getService();
        NutritionalConsultationResponseDTO response = service.findByID(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<NutritionalConsultationResponseDTO>> findAll() {
        SessionService<?, ?, NutritionalConsultationResponseDTO> service = getService();
        Long nutritionistId = getAuthenticatedNutritionistId();

        List<NutritionalConsultationResponseDTO> response = service.findAll(nutritionistId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        SessionService<?, ?, ?> service = getService();
        service.delete(id);
        return ResponseEntity.ok("Consulta deletada com sucesso."); 
    }

    /**
     * Método privado para obter o serviço da fábrica, evitando repetição.
     * Faz o cast para os tipos genéricos esperados para este controller.
     */
    @SuppressWarnings("unchecked")
    private SessionService<?, NutritionalConsultationCreateDTO, NutritionalConsultationResponseDTO> getService() {
        return (SessionService<?, NutritionalConsultationCreateDTO, NutritionalConsultationResponseDTO>) appFactory.createSessionService();
    }

    /**
     * Obtém o ID do Nutricionista autenticado no sistema.
     */
    private Long getAuthenticatedNutritionistId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        NutritionistEntity nutritionist = (NutritionistEntity) auth.getPrincipal(); // Faz o cast para NutritionistEntity
        return nutritionist.getId();
    }
}
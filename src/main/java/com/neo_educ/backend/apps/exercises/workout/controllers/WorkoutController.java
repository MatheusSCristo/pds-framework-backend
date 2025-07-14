package com.neo_educ.backend.apps.exercises.workout.controllers;

import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutCreateDTO;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutIaContentDTO;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutResponseDTO;
import com.neo_educ.backend.apps.exercises.workout.service.WorkoutService;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
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
@RequestMapping("/exercises/class-plans")
public class WorkoutController {

    @Autowired
    @Qualifier("exercisesFactory")
    private ApplicationFactory appFactory;

    @PostMapping()
    public ResponseEntity<WorkoutResponseDTO> createClassPlan(@Valid @RequestBody WorkoutCreateDTO dto) {
        SessionService<WorkoutCreateDTO, WorkoutCreateDTO, WorkoutResponseDTO> service = getService();
        Long personalId = getAuthenticatedPersonalId();

        WorkoutResponseDTO response = service.create(dto, personalId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> findById(@PathVariable Long id) {
        SessionService<?, ?, WorkoutResponseDTO> service = getService();
        WorkoutResponseDTO response = service.findByID(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<WorkoutResponseDTO>> findAll() {
        SessionService<?, ?, WorkoutResponseDTO> service = getService();
        Long personalId = getAuthenticatedPersonalId();

        List<WorkoutResponseDTO> response = service.findAll(personalId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        SessionService<?, ?, ?> service = getService();
        service.delete(id);
        return ResponseEntity.ok("Plano de Aula deletado com sucesso.");
    }

    @PutMapping("/roteiro-ia/{id}")
    public ResponseEntity<WorkoutResponseDTO> generateIA(@PathVariable Long id, @RequestBody WorkoutIaContentDTO body) {
        WorkoutService concreteService = (WorkoutService) appFactory.createSessionService();
        WorkoutResponseDTO response = concreteService.patchAiGeneratedContent(id, body.input());
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unchecked")
    private SessionService<WorkoutCreateDTO, WorkoutCreateDTO, WorkoutResponseDTO> getService() {
        return (SessionService<WorkoutCreateDTO, WorkoutCreateDTO, WorkoutResponseDTO>) appFactory.createSessionService();
    }

    private Long getAuthenticatedPersonalId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PersonalEntity personal = (PersonalEntity) auth.getPrincipal();
        return personal.getId();
    }
}
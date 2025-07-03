package com.neo_educ.backend.apps.english.classplans.controllers;

import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansIaContentDTO;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.apps.english.classplans.service.ClassPlansService;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
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
@RequestMapping("/class-plans")
public class ClassPlansController {

    @Autowired
    @Qualifier("englishFactory")
    private ApplicationFactory appFactory;

    @PostMapping()
    public ResponseEntity<ClassPlansResponseDTO> createClassPlan(@Valid @RequestBody ClassPlansCreateDTO dto) {
        //Obtém a instância do serviço a partir da fábrica.
        SessionService<ClassPlansCreateDTO, ?, ClassPlansResponseDTO> service = getService();
        Long teacherId = getAuthenticatedTeacherId();

        ClassPlansResponseDTO response = service.create(dto, teacherId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassPlansResponseDTO> findById(@PathVariable Long id) {
        SessionService<?, ?, ClassPlansResponseDTO> service = getService();
        ClassPlansResponseDTO response = service.findByID(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<ClassPlansResponseDTO>> findAll() {
        SessionService<?, ?, ClassPlansResponseDTO> service = getService();
        Long teacherId = getAuthenticatedTeacherId();

        List<ClassPlansResponseDTO> response = service.findAll(teacherId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        SessionService<?, ?, ?> service = getService();
        service.delete(id);
        return ResponseEntity.ok("Plano de Aula deletado com sucesso.");
    }

    @PutMapping("/roteiro-ia/{id}")
    public ResponseEntity<ClassPlansResponseDTO> generateIA(@PathVariable Long id, @RequestBody ClassPlansIaContentDTO body) {
        ClassPlansService concreteService = (ClassPlansService) appFactory.createSessionService();
        ClassPlansResponseDTO response = concreteService.patchAiGeneratedContent(id, body.input());
        return ResponseEntity.ok(response);
    }

    /**
     * Método privado para obter o serviço da fábrica, evitando repetição.
     * Faz o cast para os tipos genéricos esperados.
     */
    @SuppressWarnings("unchecked")
    private SessionService<ClassPlansCreateDTO, ?, ClassPlansResponseDTO> getService() {
        return (SessionService<ClassPlansCreateDTO, ?, ClassPlansResponseDTO>) appFactory.createSessionService();
    }

    private Long getAuthenticatedTeacherId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TeacherEntity teacher = (TeacherEntity) auth.getPrincipal();
        return teacher.getId();
    }
}
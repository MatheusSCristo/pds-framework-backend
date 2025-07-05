package com.neo_educ.backend.apps.exercises.athlete.controllers;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteRegisterDTO;
import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.apps.exercises.athlete.service.AthleteService;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
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
@RequestMapping("/students")
public class AthleteController {

    @Autowired
    @Qualifier("exercisesFactory")
    private ApplicationFactory appFactory;

    @PostMapping("")
    public ResponseEntity<AthleteResponseDTO> create(@RequestBody AthleteRegisterDTO studentRequestDTO) {
        AthleteService service = (AthleteService) appFactory.createClientService();
        Long teacherId = getAuthenticatedTeacherId();


        AthleteResponseDTO newAthlete = service.create(studentRequestDTO, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAthlete);
    }

    @GetMapping("")
    public ResponseEntity<List<AthleteResponseDTO>> findAllByTeacher() {
        AthleteService service = (AthleteService) appFactory.createClientService();
        Long teacherId = getAuthenticatedTeacherId();

        List<AthleteResponseDTO> students = service.findAll(teacherId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<AthleteResponseDTO> findById(@PathVariable Long studentId) {
        AthleteService service = (AthleteService) appFactory.createClientService();

        AthleteResponseDTO student = service.findById(studentId);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> delete(@PathVariable Long studentId) {
        AthleteService service = (AthleteService) appFactory.createClientService();
        
        service.delete(studentId);
        return ResponseEntity.noContent().build();
    }
    
    private Long getAuthenticatedTeacherId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonalEntity teacher = (PersonalEntity) authentication.getPrincipal();
        return teacher.getId();
    }
}
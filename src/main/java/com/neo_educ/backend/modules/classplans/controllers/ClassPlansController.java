package com.neo_educ.backend.modules.classplans.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo_educ.backend.modules.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansIaContentDTO;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansUpdateDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.mappers.ClassPlansMapper;
import com.neo_educ.backend.modules.classplans.service.ClassPlansService;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/class-plans")
public class ClassPlansController {

  @Autowired
  private ClassPlansService service;

  @Autowired
  private ClassPlansMapper mapper;

  @PostMapping()
  public ResponseEntity<ClassPlansResponseDTO> createClassPlan(@Valid @RequestBody ClassPlansCreateDTO dto) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    TeacherEntity teacher = (TeacherEntity) auth.getPrincipal();
    Long teacherId = teacher.getId();

    ClassPlansResponseDTO response= this.service.create(dto, teacherId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClassPlansResponseDTO> findById(@PathVariable Long id) {

    ClassPlansResponseDTO response = this.service.findByID(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping()
  public ResponseEntity<List<ClassPlansResponseDTO>> findAll() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    TeacherEntity teacher = (TeacherEntity) auth.getPrincipal();
    Long teacherId = teacher.getId();

    List<ClassPlansResponseDTO> response = this.service.findAll(teacherId);

    return ResponseEntity.ok(response);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {

    this.service.delete(id);

    return ResponseEntity.ok("Plano de Aula deletado com sucesso.");
  }

  @PutMapping("roteiro-ia/{id}")
  public ResponseEntity<ClassPlansResponseDTO> generateIA(@PathVariable Long id, @RequestBody ClassPlansIaContentDTO body) {

    ClassPlansResponseDTO response = this.service.patchAiGeneratedContent(id, body.input());
    return ResponseEntity.ok(response);
  }
  
  
}

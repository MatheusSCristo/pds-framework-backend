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
import com.neo_educ.backend.modules.classplans.dto.ClassPlansOutputDTO;
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
  public ResponseEntity<ClassPlansOutputDTO> createClassPlan(@Valid @RequestBody ClassPlansCreateDTO dto) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    TeacherEntity teacher = (TeacherEntity) auth.getPrincipal();
    Long teacherId = teacher.getId();

    ClassPlansEntity entity = this.service.create(dto, teacherId);
    ClassPlansOutputDTO response = this.mapper.toDTO(entity);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClassPlansOutputDTO> findById(@PathVariable Long id) {

    ClassPlansEntity entity = this.service.findByID(id);
    ClassPlansOutputDTO dto = this.mapper.toDTO(entity);

    return ResponseEntity.ok(dto);
  }

  @GetMapping()
  public ResponseEntity<List<ClassPlansOutputDTO>> findAll() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    TeacherEntity teacher = (TeacherEntity) auth.getPrincipal();
    Long teacherId = teacher.getId();

    List<ClassPlansEntity> entities = this.service.findAll(teacherId);
    List<ClassPlansOutputDTO> response = this.mapper.toManyDTO(entities);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ClassPlansOutputDTO> update(@PathVariable Long id, @Valid @RequestBody ClassPlansUpdateDTO dto) {

    ClassPlansEntity entity = this.service.update(id, dto);
    ClassPlansOutputDTO response = this.mapper.toDTO(entity);

    return ResponseEntity.ok(response);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {

    this.service.delete(id);

    return ResponseEntity.ok("Plano de Aula deletado com sucesso.");
  }

  @PutMapping("roteiro-ia/{id}")
  public ResponseEntity<ClassPlansOutputDTO> generateIA(@PathVariable Long id, @RequestBody ClassPlansIaContentDTO body) {

    ClassPlansEntity entity = this.service.patchAiGeneratedContent(id, body.input());
    ClassPlansOutputDTO response = this.mapper.toDTO(entity);

    return ResponseEntity.ok(response);
  }
  
  
}

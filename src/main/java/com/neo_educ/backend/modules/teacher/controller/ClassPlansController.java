package com.neo_educ.backend.modules.teacher.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo_educ.backend.dto.class_plans.ClassPlansCreateDTO;
import com.neo_educ.backend.dto.class_plans.ClassPlansOutputDTO;
import com.neo_educ.backend.dto.class_plans.ClassPlansUpdateDTO;
import com.neo_educ.backend.model.ClassPlansEntity;
import com.neo_educ.backend.modules.teacher.mappers.ClassPlansMapper;
import com.neo_educ.backend.service.ClassPlansService;

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
  public ResponseEntity<ClassPlansOutputDTO> createClassPlan(@RequestBody ClassPlansCreateDTO dto) {
    ClassPlansEntity entity = this.service.create(dto);
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
    List<ClassPlansEntity> entities = this.service.findAll();
    List<ClassPlansOutputDTO> response = this.mapper.toManyDTO(entities);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ClassPlansOutputDTO> update(@PathVariable Long id, @RequestBody ClassPlansUpdateDTO dto) {
    ClassPlansEntity entity = this.service.update(id, dto);
    ClassPlansOutputDTO response = this.mapper.toDTO(entity);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    this.service.delete(id);

    return ResponseEntity.ok("Plano de Aula deletado com sucesso.");
  }
  
}

package com.neo_educ.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo_educ.backend.dto.class_plans.ClassPlansCreateDTO;
import com.neo_educ.backend.dto.class_plans.ClassPlansFindByIdDTO;
import com.neo_educ.backend.mappers.ClassPlansMapper;
import com.neo_educ.backend.model.ClassPlansEntity;
import com.neo_educ.backend.service.ClassPlansService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/class-plans")
public class ClassPlansController {

  @Autowired
  private ClassPlansService service;

  @Autowired
  private ClassPlansMapper mapper;

  @PostMapping()
  public ResponseEntity<ClassPlansEntity> createClassPlan(@RequestBody ClassPlansCreateDTO dto) {
    ClassPlansEntity response = this.service.create(dto);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClassPlansFindByIdDTO> findById(@PathVariable Long id) {
    ClassPlansEntity entity = this.service.findByID(id);
    ClassPlansFindByIdDTO dto = this.mapper.toFindByIdDTO(entity);

    return ResponseEntity.ok(dto);
  }

  @GetMapping()
  public ResponseEntity<List<ClassPlansFindByIdDTO>> findAll() {
    List<ClassPlansEntity> entities = this.service.findAll();
    List<ClassPlansFindByIdDTO> response = this.mapper.toFindAll(entities);

    return ResponseEntity.ok(response);
  }

}

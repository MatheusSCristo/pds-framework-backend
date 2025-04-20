package com.neo_educ.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo_educ.backend.dto.class_plans.ClassPlansCreateDTO;
import com.neo_educ.backend.model.ClassPlansEntity;
import com.neo_educ.backend.service.ClassPlansService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/class-plans")
public class ClassPlansController {
  
  private final ClassPlansService service; 

  ClassPlansController(ClassPlansService service) {
    this.service = service;
  }

  @PostMapping()
  public ResponseEntity<ClassPlansEntity> createClassPlan(@RequestBody ClassPlansCreateDTO dto) {
    ClassPlansEntity response = this.service.create(dto);
    return ResponseEntity.ok(response);
  }
  

}

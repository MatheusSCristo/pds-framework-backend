package com.neo_educ.backend.modules.classplans.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo_educ.backend.modules.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansOutputDTO;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansUpdateDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.mappers.ClassPlansMapper;
import com.neo_educ.backend.modules.classplans.useCase.CreateClassPlansUseCase;
import com.neo_educ.backend.modules.classplans.useCase.DeleteClassPlansUseCase;
import com.neo_educ.backend.modules.classplans.useCase.FindAllClassPlansUseCase;
import com.neo_educ.backend.modules.classplans.useCase.FindByIdClassPlansUseCase;
import com.neo_educ.backend.modules.classplans.useCase.UpdateClassPlansUseCase;

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
  private CreateClassPlansUseCase create;

  @Autowired
  private UpdateClassPlansUseCase update;

  @Autowired
  private DeleteClassPlansUseCase delete;

  @Autowired
  private FindAllClassPlansUseCase findAll;

  @Autowired
  private FindByIdClassPlansUseCase findByID;
  
  @Autowired
  private ClassPlansMapper mapper;

  @PostMapping()
  public ResponseEntity<ClassPlansOutputDTO> createClassPlan(@RequestBody ClassPlansCreateDTO dto) {
    ClassPlansEntity entity = this.create.execute(dto);
    ClassPlansOutputDTO response = this.mapper.toDTO(entity);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClassPlansOutputDTO> findById(@PathVariable Long id) {
    ClassPlansEntity entity = this.findByID.execute(id);
    ClassPlansOutputDTO dto = this.mapper.toDTO(entity);

    return ResponseEntity.ok(dto);
  }

  @GetMapping()
  public ResponseEntity<List<ClassPlansOutputDTO>> findAll() {
    List<ClassPlansEntity> entities = this.findAll.execute();
    List<ClassPlansOutputDTO> response = this.mapper.toManyDTO(entities);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ClassPlansOutputDTO> update(@PathVariable Long id, @RequestBody ClassPlansUpdateDTO dto) {
    ClassPlansEntity entity = this.update.execute(id, dto);
    ClassPlansOutputDTO response = this.mapper.toDTO(entity);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    this.delete.execute(id);

    return ResponseEntity.ok("Plano de Aula deletado com sucesso.");
  }
  
}

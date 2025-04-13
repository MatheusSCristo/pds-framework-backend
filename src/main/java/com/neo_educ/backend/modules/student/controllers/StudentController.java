package com.neo_educ.backend.modules.student.controllers;

import com.neo_educ.backend.modules.student.dto.CreateStudentRequestDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.useCase.CreateStudentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

  @Autowired
  private CreateStudentUseCase createStudentUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody CreateStudentRequestDTO createStudentRequestDTO) {
    var result= createStudentUseCase.execute(createStudentRequestDTO);

    return ResponseEntity.ok().body(result);
  }

}

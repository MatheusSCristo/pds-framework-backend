package com.neo_educ.backend.modules.student.controllers;

import com.neo_educ.backend.modules.student.dto.CreateStudentRequestDTO;
import com.neo_educ.backend.modules.student.useCase.CreateStudentUseCase;
import com.neo_educ.backend.modules.student.useCase.DeleteStudentUseCase;
import com.neo_educ.backend.modules.student.useCase.GetStudentInformationsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

  @Autowired
  private CreateStudentUseCase createStudentUseCase;

  @Autowired
  private GetStudentInformationsUseCase getStudentInformationsUseCase;

  @Autowired
  private DeleteStudentUseCase deleteStudentUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody CreateStudentRequestDTO createStudentRequestDTO) {
    try {
      var result= createStudentUseCase.execute(createStudentRequestDTO);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{studentId}")
  public ResponseEntity<Object> getStudentInformations(@PathVariable Long studentId) {
    try {
      var result = getStudentInformationsUseCase.execute(studentId);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{studentId}")
  public ResponseEntity<Object> delete(@PathVariable Long studentId) {
    try {
      this.deleteStudentUseCase.execute(studentId);

      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}

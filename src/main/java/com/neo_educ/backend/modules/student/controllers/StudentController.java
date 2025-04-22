package com.neo_educ.backend.modules.student.controllers;

import com.neo_educ.backend.modules.student.dto.StudentRequestDTO;
import com.neo_educ.backend.modules.student.useCase.CreateStudentUseCase;
import com.neo_educ.backend.modules.student.useCase.DeleteStudentUseCase;
import com.neo_educ.backend.modules.student.useCase.GetStudentInformationsUseCase;
import com.neo_educ.backend.modules.student.useCase.UpdateStudentInformationsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @Autowired
  private UpdateStudentInformationsUseCase updateStudentInformationsUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody StudentRequestDTO studentRequestDTO) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      var result= createStudentUseCase.execute(studentRequestDTO, teacherEmail);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{studentId}")
  public ResponseEntity<Object> getStudentInformations(@PathVariable Long studentId) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      var result = getStudentInformationsUseCase.execute(studentId, teacherEmail);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{studentId}")
  public ResponseEntity<Object> delete(@PathVariable Long studentId) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      this.deleteStudentUseCase.execute(studentId, teacherEmail);

      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{studentId}")
  public ResponseEntity<Object> updateStudentInformations(@PathVariable Long studentId, @RequestBody StudentRequestDTO studentRequestDTO) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String teacherEmail = authentication.getName();

      var result = this.updateStudentInformationsUseCase.execute(studentRequestDTO, studentId, teacherEmail);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}

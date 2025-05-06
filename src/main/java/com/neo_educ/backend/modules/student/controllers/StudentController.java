package com.neo_educ.backend.modules.student.controllers;

import com.neo_educ.backend.core.response.ApiResponse;
import com.neo_educ.backend.exceptions.student.StudentAlreadyExistsException;
import com.neo_educ.backend.modules.student.dto.StudentRequestDTO;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            studentService.createStudent(studentRequestDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (StudentAlreadyExistsException e) {
            ApiResponse apiResponse = new ApiResponse(
                    "Usuário já existente"
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }
        catch(EntityNotFoundException e){
            ApiResponse apiResponse = new ApiResponse("Link de cadastro inválido");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse());
        }

    }


    @GetMapping("/{studentId}")
    public ResponseEntity<Object> getStudentInformations(@PathVariable Long studentId) {
        try {
            StudentResponseDTO student = studentService.findStudent(studentId);
            return ResponseEntity.ok().body(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> delete(@PathVariable Long studentId) {
        try {
            studentService.deleteStudent(studentId);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Object> updateStudentInformations(@PathVariable Long studentId, @RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            var result = this.studentService.updateStudent(studentRequestDTO, studentId);

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

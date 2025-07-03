package com.neo_educ.backend.apps.english.student.controllers;

import com.neo_educ.backend.apps.english.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.service.StudentService;
import com.neo_educ.backend.core.response.ApiResponse;
import com.neo_educ.backend.exceptions.student.StudentAlreadyExistsException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestBody StudentRegisterDTO studentRequestDTO) {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }

    }

    @GetMapping("")
    public ResponseEntity<Object> getTeacherStudents() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String teacherEmail = authentication.getName();
            List<StudentResponseDTO> student = studentService.getTeacherStudents(teacherEmail);
            return ResponseEntity.ok().body(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Object> getStudentInformations(@PathVariable Long studentId) {
        try {
            StudentResponseDTO student = studentService.findStudentDTO(studentId);
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


}

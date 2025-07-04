package com.neo_educ.backend.apps.english.student.controllers;

import com.neo_educ.backend.apps.english.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.service.StudentService;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("englishFactory")
    private ApplicationFactory appFactory;

    @PostMapping("")
    public ResponseEntity<StudentResponseDTO> create(@RequestBody StudentRegisterDTO studentRequestDTO) {
        StudentService service = (StudentService) appFactory.createClientService();
        Long teacherId = getAuthenticatedTeacherId();


        StudentResponseDTO newStudent = service.create(studentRequestDTO, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @GetMapping("")
    public ResponseEntity<List<StudentResponseDTO>> findAllByTeacher() {
        StudentService service = (StudentService) appFactory.createClientService();
        Long teacherId = getAuthenticatedTeacherId();

        List<StudentResponseDTO> students = service.findAll(teacherId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDTO> findById(@PathVariable Long studentId) {
        StudentService service = (StudentService) appFactory.createClientService();

        StudentResponseDTO student = service.findById(studentId);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> delete(@PathVariable Long studentId) {
        StudentService service = (StudentService) appFactory.createClientService();
        
        service.delete(studentId);
        return ResponseEntity.noContent().build();
    }
    
    private Long getAuthenticatedTeacherId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeacherEntity teacher = (TeacherEntity) authentication.getPrincipal();
        return teacher.getId();
    }
}
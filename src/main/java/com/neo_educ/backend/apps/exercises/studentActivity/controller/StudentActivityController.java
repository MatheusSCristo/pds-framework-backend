package com.neo_educ.backend.apps.exercises.studentActivity.controller;

import com.neo_educ.backend.apps.exercises.studentActivity.dto.StudentActivityCreateDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.dto.StudentActivityResponseDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.service.StudentActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class StudentActivityController {


    @Autowired
    private StudentActivityService studentActivityService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<StudentActivityResponseDTO>> getAllStudentActivity(
            @PathVariable Long studentId
    ) {
        List<StudentActivityResponseDTO> studentActivityResponseDTOS = studentActivityService.getStudentActivities(studentId);
        return ResponseEntity.ok(studentActivityResponseDTOS);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createStudentActivity(
            @RequestBody StudentActivityCreateDTO studentActivityCreateDTO
    ) {
        studentActivityService.createStudentActivity(studentActivityCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

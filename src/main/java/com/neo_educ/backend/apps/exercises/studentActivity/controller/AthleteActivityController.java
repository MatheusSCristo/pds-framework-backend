package com.neo_educ.backend.apps.exercises.studentActivity.controller;

import com.neo_educ.backend.apps.exercises.studentActivity.dto.AthleteActivityCreateDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.dto.AthleteActivityResponseDTO;
import com.neo_educ.backend.apps.exercises.studentActivity.service.AthleteActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises/athlete/activity")
public class AthleteActivityController {

    @Autowired
    private AthleteActivityService athleteActivityService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<AthleteActivityResponseDTO>> getAllStudentActivity(
            @PathVariable Long studentId
    ) {
        List<AthleteActivityResponseDTO> studentActivityResponseDTOS = athleteActivityService.getStudentActivities(studentId);
        return ResponseEntity.ok(studentActivityResponseDTOS);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createStudentActivity(
            @RequestBody AthleteActivityCreateDTO studentActivityCreateDTO
    ) {
        athleteActivityService.createStudentActivity(studentActivityCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

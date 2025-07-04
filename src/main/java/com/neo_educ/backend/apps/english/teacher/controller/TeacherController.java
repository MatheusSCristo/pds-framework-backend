package com.neo_educ.backend.apps.english.teacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo_educ.backend.apps.english.teacher.dto.TeacherResponseDTO;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.mappers.TeacherMapper;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherMapper teacherMapper;

    @GetMapping("/me")
    public ResponseEntity<TeacherResponseDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeacherEntity currentUser = (TeacherEntity) authentication.getPrincipal();
        return ResponseEntity.ok(teacherMapper.toDTO(currentUser));
    }
}

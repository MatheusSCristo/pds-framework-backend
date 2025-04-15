package com.neo_educ.backend.controller;

import com.neo_educ.backend.dto.auth.TeacherDTO;
import com.neo_educ.backend.mappers.TeacherMapper;
import com.neo_educ.backend.model.TeacherEntity;
import com.neo_educ.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {


    private final TeacherMapper teacherMapper;


    @Autowired
    TeacherController(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<TeacherDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TeacherEntity currentUser =  (TeacherEntity) authentication.getPrincipal();

        return ResponseEntity.ok(teacherMapper.toDTO(currentUser));
    }
}

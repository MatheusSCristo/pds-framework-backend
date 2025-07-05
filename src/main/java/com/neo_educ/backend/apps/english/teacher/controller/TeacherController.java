package com.neo_educ.backend.apps.english.teacher.controller;

import com.neo_educ.backend.apps.english.teacher.dto.TeacherResponseDTO;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.mappers.TeacherMapper;
import com.neo_educ.backend.core.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController extends UserController<TeacherEntity, TeacherResponseDTO> {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    protected TeacherResponseDTO mapToDto(TeacherEntity entity) {
        return teacherMapper.toDTO(entity);
    }

    public ResponseEntity<TeacherResponseDTO> authenticatedUser() {
        return super.authenticatedUser();
    }
}

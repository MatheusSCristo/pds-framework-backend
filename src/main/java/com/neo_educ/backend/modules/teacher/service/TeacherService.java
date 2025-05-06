package com.neo_educ.backend.modules.teacher.service;

import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherEntity findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Entity with email " + email + " not found"));
    }

    public TeacherEntity findTeacherByToken(String token) {
        return teacherRepository.findByInviteToken(token).orElseThrow(() -> new EntityNotFoundException("Entity with token " + token + " not found"));

    }

}

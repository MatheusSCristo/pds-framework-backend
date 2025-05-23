package com.neo_educ.backend.modules.auth.service;

import com.neo_educ.backend.exceptions.teacher.TeacherAlreadyRegisteredException;
import com.neo_educ.backend.modules.auth.dto.LoginDTO;
import com.neo_educ.backend.modules.auth.dto.RegisterDTO;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;
import com.neo_educ.backend.modules.teacher.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TeacherEntity signIn(LoginDTO input) {
        return teacherService.findTeacherByEmail(input.email());
    }

    public TeacherEntity signUp(RegisterDTO input) {

        Optional<TeacherEntity> existingTeacher = teacherRepository.findByEmail(input
                .email());
        if (existingTeacher.isPresent()) {
            throw new TeacherAlreadyRegisteredException(input.email());
        }

        String encodedPassword = passwordEncoder.encode(input.password());
        String token = UUID.randomUUID().toString().replace("-", "");
        TeacherEntity user = TeacherEntity.builder()
                .name(input.name())
                .lastName(input.lastName())
                .email(input.email())
                .phone(input.phone())
                .password(encodedPassword)
                .inviteToken(token)
                .build();


        return teacherRepository.save(user);
    }
}

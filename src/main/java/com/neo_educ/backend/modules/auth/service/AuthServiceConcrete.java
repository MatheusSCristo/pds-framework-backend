// src/main/java/com/neo_educ/backend/modules/auth/service/AuthService.java
package com.neo_educ.backend.modules.auth.service;

import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.core.service.AuthServiceInterface;
import com.neo_educ.backend.exceptions.teacher.TeacherAlreadyRegisteredException;
import com.neo_educ.backend.modules.auth.dto.LoginDTO;
import com.neo_educ.backend.modules.auth.dto.RegisterDTO;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;
import com.neo_educ.backend.modules.teacher.service.TeacherService; // Agora é o UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceConcrete implements AuthServiceInterface { // Implementa a interface AuthService

    private final TeacherService teacherService; // Usa o TeacherService como UserService concreto
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceConcrete(TeacherService teacherService, TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity signIn(LoginDTO input) {
        return teacherService.findTeacherByEmail(input.email());
    }

    @Override
    public UserEntity signUp(RegisterDTO input) {
        Optional<TeacherEntity> existingTeacher = teacherRepository.findByEmail(input.email());
        if (existingTeacher.isPresent()) {
            throw new TeacherAlreadyRegisteredException();
        }

        String encodedPassword = passwordEncoder.encode(input.password());
        String token = UUID.randomUUID().toString().replace("-", "");

        TeacherEntity newTeacher = TeacherEntity.builder()
                .name(input.name())
                .lastName(input.lastName())
                .email(input.email())
                .phone(input.phone())
                .password(encodedPassword)
                .inviteToken(token)
                .build();

        return teacherRepository.save(newTeacher);
    }
}
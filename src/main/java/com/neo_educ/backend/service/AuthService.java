package com.neo_educ.backend.service;

import com.neo_educ.backend.dto.auth.LoginDTO;
import com.neo_educ.backend.dto.auth.RegisterDTO;
import com.neo_educ.backend.exceptions.teacher.TeacherAlreadyRegisteredException;
import com.neo_educ.backend.model.TeacherEntity;
import com.neo_educ.backend.repository.TeacherRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final TeacherRepository teacherRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(
            TeacherRepository teacherRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public TeacherEntity signup(RegisterDTO input) {

        Optional<TeacherEntity> existingTeacher=teacherRepository.findByEmail(input
                .email());
        if (existingTeacher.isPresent()) {
            throw new TeacherAlreadyRegisteredException(input.email());
        }

        String encodedPassword=passwordEncoder.encode(input.password());
        TeacherEntity user = TeacherEntity.builder()
                .name(input.name())
                .lastName(input.lastName())
                .email(input.email())
                .city(input.city())
                .phone(input.phone())
                .country(input.country())
                .state(input.state())
                .password(encodedPassword)
                .build();
        return teacherRepository.save(user);
    }

    public TeacherEntity authenticate(LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return teacherRepository.findByEmail(input.email())
                .orElseThrow();
    }


}

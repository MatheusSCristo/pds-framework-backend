package com.neo_educ.backend.modules.auth.useCase;

import com.neo_educ.backend.exceptions.teacher.TeacherAlreadyRegisteredException;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.auth.dto.RegisterDTO;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupUseCase {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TeacherEntity execute(RegisterDTO input) {

        Optional<TeacherEntity> existingTeacher = teacherRepository.findByEmail(input
                .email());
        if (existingTeacher.isPresent()) {
            throw new TeacherAlreadyRegisteredException(input.email());
        }

        String encodedPassword = passwordEncoder.encode(input.password());
        TeacherEntity user = TeacherEntity.builder()
                .name(input.name())
                .lastName(input.lastName())
                .email(input.email())
                .phone(input.phone())
                .password(encodedPassword)
                .build();
        return teacherRepository.save(user);
    }
}

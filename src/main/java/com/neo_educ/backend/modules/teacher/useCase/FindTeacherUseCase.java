package com.neo_educ.backend.modules.teacher.useCase;

import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FindTeacherUseCase {

    @Autowired
    private TeacherRepository teacherRepository;

    public UserDetails execute(String email) {
        return teacherRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Entity with email " + email + " not found"));
    }
}

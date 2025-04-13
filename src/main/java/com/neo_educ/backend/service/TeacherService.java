package com.neo_educ.backend.service;

import com.neo_educ.backend.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    public UserDetails findByEmailOrThrowError(String email) {
        return teacherRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Entity with email " + email + " not found"));
    }
}

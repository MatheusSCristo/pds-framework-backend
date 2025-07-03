// src/main/java/com/neo_educ/backend/modules/teacher/service/TeacherService.java
package com.neo_educ.backend.modules.teacher.service;

import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.core.service.UserService; // Importa a interface genérica
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TeacherService implements UserService { // Implementa UserService

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Usado pelo Spring Security para autenticação
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Professor com email " + email + " não encontrado"));
    }

    @Override
    public UserEntity signUp(UserEntity user) {
        // Este método não deve ser chamado diretamente para Teacher signup neste modelo,
        // pois RegisterDTO é mais específico e tratado por AuthService.
        // Se fosse para um sistema mais genérico, você teria um Mapper aqui.
        // Por enquanto, mantenha a implementação para satisfazer a interface.
        if (!(user instanceof TeacherEntity)) {
            throw new IllegalArgumentException("User must be a TeacherEntity for TeacherService signup.");
        }
        return teacherRepository.save((TeacherEntity) user);
    }

    public TeacherEntity findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Professor com email " + email + " não encontrado"));
    }

    public TeacherEntity findTeacherByToken(String token) {
        return teacherRepository.findByInviteToken(token).orElseThrow(() -> new EntityNotFoundException("Professor com token " + token + " não encontrado"));
    }
}
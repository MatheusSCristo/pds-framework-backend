package com.neo_educ.backend.apps.english.teacher.service;

import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.repository.TeacherRepository;
import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.core.service.UserService;
import com.neo_educ.backend.exceptions.teacher.TeacherAlreadyRegisteredException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("teacherService")
public class TeacherService implements UserService {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Override
    public UserEntity signUp(UserEntity user) {
        teacherRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new TeacherAlreadyRegisteredException();
        });

        TeacherEntity teacher = (TeacherEntity) user;

        String token = UUID.randomUUID().toString().replace("-", "");
        teacher.setInviteToken(token);

        return teacherRepository.save(teacher);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Professor não encontrado com o email: " + email));
    }
    
    public TeacherEntity findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Entidade com email " + email + " não encontrada"));
    }

    public TeacherEntity findTeacherByToken(String token) {
        return teacherRepository.findByInviteToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Entidade com token " + token + " não encontrada"));
    }
}
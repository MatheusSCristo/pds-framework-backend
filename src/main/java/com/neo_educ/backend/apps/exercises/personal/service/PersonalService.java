package com.neo_educ.backend.apps.exercises.personal.service;

import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.personal.repository.PersonalRepository;
import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.core.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("personalService")
public class PersonalService implements UserService {

    @Autowired
    private PersonalRepository teacherRepository;
    
    @Override
    public UserEntity signUp(UserEntity user) {
        teacherRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException();
        });

        PersonalEntity teacher = (PersonalEntity) user;

        String token = UUID.randomUUID().toString().replace("-", "");
        teacher.setInviteToken(token);

        return teacherRepository.save(teacher);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Professor não encontrado com o email: " + email));
    }
    
    public PersonalEntity findPersonalByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Entidade com email " + email + " não encontrada"));
    }

    public PersonalEntity findPersonalByToken(String token) {
        return teacherRepository.findByInviteToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Entidade com token " + token + " não encontrada"));
    }
}
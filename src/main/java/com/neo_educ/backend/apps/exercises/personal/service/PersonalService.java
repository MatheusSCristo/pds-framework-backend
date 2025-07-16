package com.neo_educ.backend.apps.exercises.personal.service;

import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.personal.repository.PersonalRepository;
import com.neo_educ.backend.core.repository.AbstractRepository;
import com.neo_educ.backend.core.repository.AbstractUserRepository;
import com.neo_educ.backend.core.service.UserService;
import com.neo_educ.backend.exceptions.ApiException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("personalService")
public class PersonalService implements UserService<PersonalEntity> {

    @Autowired
    private PersonalRepository personalRepository;

    @Override
    public AbstractUserRepository<PersonalEntity> getRepository() {
        return personalRepository;
    }

    @Override
    public ApiException getAlreadyExistsException() {
        return new ApiException(HttpStatus.CONFLICT, "Um personal com este e-mail já está cadastrado.");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personalRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Personal não encontrado com o email: " + email));
    }

    public PersonalEntity findPersonalByEmail(String email) {
        return personalRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Entidade com email " + email + " não encontrada"));
    }

    public PersonalEntity findPersonalByToken(String token) {
        return personalRepository.findByInviteToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Entidade com token " + token + " não encontrada"));
    }
}
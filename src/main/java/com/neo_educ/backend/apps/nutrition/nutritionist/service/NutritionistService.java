package com.neo_educ.backend.apps.nutrition.nutritionist.service;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.repository.NutritionistRepository;
import com.neo_educ.backend.core.repository.AbstractUserRepository;
import com.neo_educ.backend.core.service.UserService;
import com.neo_educ.backend.exceptions.ApiException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("nutritionistService")
public class NutritionistService implements UserService<NutritionistEntity> {

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Override
    public AbstractUserRepository<NutritionistEntity> getRepository() {
        return nutritionistRepository;
    }

    @Override
    public ApiException getAlreadyExistsException() {
        return new ApiException(HttpStatus.CONFLICT, "Um nutricionista com este e-mail já está cadastrado.");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return nutritionistRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Nutricionista não encontrado com o email: " + email));
    }

    public NutritionistEntity findNutritionistByEmail(String email) {
        return nutritionistRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista com email " + email + " não encontrado"));
    }

    public NutritionistEntity findNutritionistByToken(String token) {
        return nutritionistRepository.findByInviteToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista com token " + token + " não encontrado"));
    }
}
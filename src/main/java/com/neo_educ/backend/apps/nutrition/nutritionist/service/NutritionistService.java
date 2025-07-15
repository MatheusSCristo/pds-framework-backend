package com.neo_educ.backend.apps.nutrition.nutritionist.service;

import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.repository.NutritionistRepository;
import com.neo_educ.backend.core.model.UserEntity;
import com.neo_educ.backend.core.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("nutritionistService")
public class NutritionistService implements UserService {

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Override
    public UserEntity signUp(UserEntity user) {
        nutritionistRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException();
        });

        NutritionistEntity nutritionist = (NutritionistEntity) user;

        String token = UUID.randomUUID().toString().replace("-", "");
        nutritionist.setInviteToken(token);

        return nutritionistRepository.save(nutritionist);
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
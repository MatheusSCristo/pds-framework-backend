package com.neo_educ.backend.modules.classplans.useCase;


import org.springframework.stereotype.Service;

import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;

import java.util.Optional;

@Service
public class FindByIdClassPlansUseCase {

    private final ClassPlansRepository classPlansRepository;

    public FindByIdClassPlansUseCase(ClassPlansRepository classPlansRepository) {
        this.classPlansRepository = classPlansRepository;
    }

    public ClassPlansEntity execute(Long id) {
        Optional<ClassPlansEntity> optionalClassPlan = classPlansRepository.findById(id);

        if (optionalClassPlan.isEmpty()) {
            throw new RuntimeException("Plano de aula não encontrado com o ID: " + id);
        }

        return optionalClassPlan.get();
    }
}

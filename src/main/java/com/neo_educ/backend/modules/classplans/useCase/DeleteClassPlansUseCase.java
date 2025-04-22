package com.neo_educ.backend.modules.classplans.useCase;


import org.springframework.stereotype.Service;

import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;

@Service
public class DeleteClassPlansUseCase {

    private final ClassPlansRepository classPlansRepository;
    private final FindByIdClassPlansUseCase findByIdClassPlansUseCase;

    public DeleteClassPlansUseCase(
        ClassPlansRepository classPlansRepository,
        FindByIdClassPlansUseCase findByIdClassPlansUseCase
    ) {
        this.classPlansRepository = classPlansRepository;
        this.findByIdClassPlansUseCase = findByIdClassPlansUseCase;
    }

    public void execute(Long id) {
        ClassPlansEntity entity = findByIdClassPlansUseCase.execute(id);
        classPlansRepository.delete(entity);
    }
}


package com.neo_educ.backend.modules.classplans.useCase;

import org.springframework.stereotype.Service;

import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;

import java.util.List;

@Service
public class FindAllClassPlansUseCase {

    private final ClassPlansRepository classPlansRepository;

    public FindAllClassPlansUseCase(ClassPlansRepository classPlansRepository) {
        this.classPlansRepository = classPlansRepository;
    }

    public List<ClassPlansEntity> execute() {
        return classPlansRepository.findAll();
    }
}

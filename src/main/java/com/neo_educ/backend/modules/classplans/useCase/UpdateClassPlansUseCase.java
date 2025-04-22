package com.neo_educ.backend.modules.classplans.useCase;


import org.springframework.stereotype.Service;

import com.neo_educ.backend.modules.classplans.dto.ClassPlansUpdateDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;

@Service
public class UpdateClassPlansUseCase {

    private final ClassPlansRepository classPlansRepository;
    private final FindByIdClassPlansUseCase findByIdClassPlansUseCase;

    public UpdateClassPlansUseCase(
        ClassPlansRepository classPlansRepository,
        FindByIdClassPlansUseCase findByIdClassPlansUseCase
    ) {
        this.classPlansRepository = classPlansRepository;
        this.findByIdClassPlansUseCase = findByIdClassPlansUseCase;
    }

    public ClassPlansEntity execute(Long id, ClassPlansUpdateDTO data) {
        ClassPlansEntity entity = findByIdClassPlansUseCase.execute(id);
        entity.setTopic(data.topic());
        entity.setClassDate(data.classDate());
        entity.setInputData(data.inputData());

        return classPlansRepository.save(entity);
    }
}

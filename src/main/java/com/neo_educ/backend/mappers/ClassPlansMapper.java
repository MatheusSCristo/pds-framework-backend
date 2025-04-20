package com.neo_educ.backend.mappers;

import org.springframework.stereotype.Component;

import com.neo_educ.backend.dto.class_plans.ClassPlansFindByIdDTO;
import com.neo_educ.backend.model.ClassPlansEntity;

@Component
public class ClassPlansMapper {

  public ClassPlansFindByIdDTO toFindByIdDTO(ClassPlansEntity entity) {
    if (entity == null) return null;

    return new ClassPlansFindByIdDTO(
      entity.getTopic(),
      entity.getClassDate(),
      entity.getInputData(),
      entity.getAiGeneratedContent(),
      entity.getStatus()
    );
  }
}

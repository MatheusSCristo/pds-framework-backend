package com.neo_educ.backend.mappers;

import java.util.List;

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

  public List<ClassPlansFindByIdDTO> toFindAll(List<ClassPlansEntity> entities) {
    if (entities == null) return List.of();
  
    return entities.stream()
      .map(this::toFindByIdDTO)
      .toList();
  }
  
}

package com.neo_educ.backend.modules.classplans.mappers;
import java.util.List;

import org.springframework.stereotype.Component;

import com.neo_educ.backend.modules.classplans.dto.ClassPlansOutputDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;


@Component
public class ClassPlansMapper {

  public ClassPlansOutputDTO toDTO(ClassPlansEntity entity) {
    if (entity == null) return null;

    return new ClassPlansOutputDTO(
      entity.getId(),
      entity.getTopic(),
      entity.getClassDate(),
      entity.getInputData(),
      entity.getAiGeneratedContent(),
      entity.getStatus()
    );
  }

  public List<ClassPlansOutputDTO> toManyDTO(List<ClassPlansEntity> entities) {
    if (entities == null) return List.of();
  
    return entities.stream()
      .map(this::toDTO)
      .toList();
  }
  
}

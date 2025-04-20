package com.neo_educ.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo_educ.backend.dto.class_plans.ClassPlansCreateDTO;
import com.neo_educ.backend.enums.ClassPlanStatus;
import com.neo_educ.backend.model.ClassPlansEntity;
import com.neo_educ.backend.model.TeacherEntity;
import com.neo_educ.backend.repository.ClassPlansRepository;
import com.neo_educ.backend.repository.TeacherRepository;

import java.util.Optional;

@Service
public class ClassPlansService {
  
  @Autowired
  private ClassPlansRepository classPlansRepository;

  @Autowired
  private TeacherRepository teacherRepository;

  public ClassPlansEntity create(ClassPlansCreateDTO data) {
    Optional<TeacherEntity> optionalTeacher = teacherRepository.findById(data.teacher_id());

    if (optionalTeacher.isEmpty()) {
      throw new RuntimeException("Professor não encontrado com ID: " + data.teacher_id());
    }

    TeacherEntity teacher = optionalTeacher.get();

    ClassPlansEntity entity = new ClassPlansEntity();
    entity.setTopic(data.topic());
    entity.setClassDate(data.classDate());
    entity.setInputData(data.inputData());
    entity.setTeacher(teacher);
    entity.setStatus(ClassPlanStatus.PENDING);

    return classPlansRepository.save(entity);
  }

  public ClassPlansEntity findByID(Long id) {
    Optional<ClassPlansEntity> optionalClassPlan = classPlansRepository.findById(id);

    if (optionalClassPlan.isEmpty()) {
      throw new RuntimeException("Plano de aula não encontrado com o ID: " + id);
    }

    return optionalClassPlan.get();
  }
}

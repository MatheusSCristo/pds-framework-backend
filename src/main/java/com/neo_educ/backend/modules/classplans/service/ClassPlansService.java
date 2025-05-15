package com.neo_educ.backend.modules.classplans.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo_educ.backend.exceptions.common.NotFoundException;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansUpdateDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.enums.ClassPlanStatus;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;

@Service
public class ClassPlansService {
  
  @Autowired
  private ClassPlansRepository classPlansRepository;

  @Autowired
  private TeacherRepository teacherRepository;

  public ClassPlansEntity create(ClassPlansCreateDTO data, Long teacherID) {

    TeacherEntity teacher = teacherRepository.findById(teacherID).orElseThrow(() -> new NotFoundException("Professor não encontrado"));
      
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
      throw new NotFoundException("Plano de aula não encontrado com o ID: " + id);
    }

    return optionalClassPlan.get();

  }



  public List<ClassPlansEntity> findAll(Long teacherID) {

    return classPlansRepository.findAllByTeacher_Id(teacherID);

  }



  public ClassPlansEntity update(Long id, ClassPlansUpdateDTO data) {

    ClassPlansEntity entity = this.findByID(id);
    entity.setTopic(data.topic());
    entity.setClassDate(data.classDate());
    entity.setInputData(data.inputData());

    return classPlansRepository.save(entity);

  }



  public void delete(Long id) {

    ClassPlansEntity entity = this.findByID(id);

    this.classPlansRepository.delete(entity);
  }

  public ClassPlansEntity patchAiGeneratedContent(Long id, String input) {
    ClassPlansEntity entity = this.findByID(id);
    entity.setAiGeneratedContent(input);
    
    return classPlansRepository.save(entity);
  }

}

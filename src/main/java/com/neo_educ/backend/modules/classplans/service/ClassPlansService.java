package com.neo_educ.backend.modules.classplans.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo_educ.backend.exceptions.common.NotFoundException;
import com.neo_educ.backend.modules.chat.service.ChatService;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansUpdateDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.enums.ClassPlanStatus;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;

@Service
public class ClassPlansService {
  
  private final String iaClassPlansContext = "Você é um assistente didático para professores de inglês. Quando o professor digitar um assunto (por exemplo: Present Perfect ou Vocabulary about Travel), você deve gerar um plano de aula completo para uma aula de inglês. O plano deve ser objetivo, prático e adaptado a estudantes de inglês como segunda língua. O professor pode usar esse plano diretamente em sala de aula.\n" + //
        "\n" + //
        "O plano deve conter:\n" + //
        "- Objetivo(s) da aula\n" + //
        "- Materiais necessários (se houver)\n" + //
        "- Duração estimada\n" + //
        "- Etapas da aula (com sugestões de atividades para introdução, prática e encerramento)\n" + //
        "- Dicas para o professor\n" + //
        "- Sugestões de dever de casa (opcional)\n" + 
        "Esse é o assunto:";

  @Autowired
  private ClassPlansRepository classPlansRepository;

  @Autowired
  private ChatService chatService;

  @Autowired
  private TeacherRepository teacherRepository;

  public ClassPlansEntity create(ClassPlansCreateDTO data, Long teacherID) {

    TeacherEntity teacher = teacherRepository.findById(teacherID).orElseThrow(() -> new NotFoundException("Professor não encontrado"));
      
    ClassPlansEntity entity = new ClassPlansEntity();

    String geminiResponse = this.chatService.chat(this.iaClassPlansContext + data.inputData());

    entity.setTopic(data.topic());
    entity.setClassDate(data.classDate());
    entity.setInputData(data.inputData());
    entity.setTeacher(teacher);
    entity.setStatus(ClassPlanStatus.PENDING);
    entity.setAiGeneratedContent(geminiResponse);

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

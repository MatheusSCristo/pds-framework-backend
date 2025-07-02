package com.neo_educ.backend.modules.classplans.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.neo_educ.backend.modules.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.modules.classplans.mappers.ClassPlansMapper;
import com.neo_educ.backend.modules.llm.service.LLMService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo_educ.backend.exceptions.ConflictException;
import com.neo_educ.backend.modules.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.enums.ClassPlanStatus;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;

@Service
public class ClassPlansService {

    private final String iaClassPlansContext = "Você é um assistente didático para professores de inglês. Quando o professor digitar um assunto (por exemplo: Present Perfect ou Vocabulary about Travel), você deve gerar um plano de aula completo para uma aula de inglês. O plano deve ser objetivo, prático e adaptado a estudantes de inglês como segunda língua. O professor pode usar esse plano diretamente em sala de aula.\n"
            + //
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
    private LLMService llmService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassPlansMapper classPlansMapper;

    public ClassPlansResponseDTO create(ClassPlansCreateDTO data, Long teacherID) {

        TeacherEntity teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        LocalDateTime classDate = data.classDate();
        LocalDateTime start = classDate.minusMinutes(30);
        LocalDateTime end = classDate.plusMinutes(30);

        Long conflicts = classPlansRepository.countConflictingPlans(teacherID, start, end);

        if (conflicts > 0) {
            throw new ConflictException("Já existe uma aula marcada nesse intervalo de 30 minutos.");
        }

        ClassPlansEntity entity = new ClassPlansEntity();

        String geminiResponse = this.llmService.chat(this.iaClassPlansContext + data.inputData());

        entity.setTopic(data.topic());
        entity.setClassDate(data.classDate());
        entity.setTopic(data.inputData());
        entity.setTeacher(teacher);
        entity.setStatus(ClassPlanStatus.PENDING);
        entity.setContent(geminiResponse);

        ClassPlansEntity classPlan = classPlansRepository.save(entity);
        return classPlansMapper.toResponse(classPlan);

    }

    public ClassPlansResponseDTO findByID(Long id) {

        Optional<ClassPlansEntity> optionalClassPlan = classPlansRepository.findById(id);

        if (optionalClassPlan.isEmpty()) {
            throw new EntityNotFoundException("Plano de aula não encontrado com o ID: " + id);
        }

        return classPlansMapper.toResponse(optionalClassPlan.get());

    }

    public List<ClassPlansResponseDTO> findAll(Long teacherID) {

        List<ClassPlansEntity> classPlans = classPlansRepository.findAllByTeacher_Id(teacherID);
        return classPlans.stream().map(classPlansMapper::toResponse).toList();

    }

    public void delete(Long id) {
        this.classPlansRepository.deleteById(id);
    }

    public ClassPlansResponseDTO patchAiGeneratedContent(Long id, String input) {
        ClassPlansEntity entity = classPlansRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano de aula não encontrado com o ID: " + id));
        entity.setContent(input);

        ClassPlansEntity classPlan = classPlansRepository.save(entity);
        return classPlansMapper.toResponse(classPlan);
    }

}

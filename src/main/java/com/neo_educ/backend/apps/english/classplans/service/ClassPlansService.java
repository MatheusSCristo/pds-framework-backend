package com.neo_educ.backend.apps.english.classplans.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.apps.english.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.apps.english.classplans.mappers.ClassPlansMapper;
import com.neo_educ.backend.apps.english.classplans.repository.ClassPlansRepository;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.repository.TeacherRepository;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.core.service.SessionService;
import com.neo_educ.backend.exceptions.ConflictException;

@Service
public class ClassPlansService implements SessionService<ClassPlansEntity, ClassPlansCreateDTO, ClassPlansResponseDTO> {

    @Autowired
    private ClassPlansRepository classPlansRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassPlansMapper classPlansMapper;

    @Autowired
    @Qualifier("englishActivityService")
    private ActivityGeneratorService activityGenerator;

    public ClassPlansResponseDTO create(ClassPlansCreateDTO data, Long teacherID) {

        TeacherEntity teacher = teacherRepository.findById(teacherID).orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        LocalDateTime classDate = data.classDate();
        LocalDateTime start = classDate.minusMinutes(30);
        LocalDateTime end = classDate.plusMinutes(30);

        Long conflicts = classPlansRepository.countConflictingPlans(teacherID, start, end);

        if (conflicts > 0) {
            throw new ConflictException("Já existe uma aula marcada nesse intervalo de 30 minutos.");
        }
        String generatedContent = activityGenerator.generateSession(data.inputData());

        ClassPlansEntity entity = new ClassPlansEntity(data.title(),
                data.inputData(),
                data.classDate(),
                teacher,
                generatedContent);
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
        ClassPlansEntity entity = classPlansRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Plano de aula não encontrado com o ID: " + id));
        entity.setContent(input);

        ClassPlansEntity classPlan = classPlansRepository.save(entity);
        return classPlansMapper.toResponse(classPlan);
    }

}

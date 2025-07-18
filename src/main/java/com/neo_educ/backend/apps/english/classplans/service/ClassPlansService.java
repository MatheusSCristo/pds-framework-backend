package com.neo_educ.backend.apps.english.classplans.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
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
public class ClassPlansService
        implements SessionService<ClassPlansEntity, ClassPlansCreateDTO, ClassPlansResponseDTO, TeacherEntity> {

    private final ClassPlansRepository classPlansRepository;
    private final TeacherRepository teacherRepository;
    private final ClassPlansMapper classPlansMapper;
    private final ActivityGeneratorService activityGenerator;

    public ClassPlansService(
            ClassPlansRepository classPlansRepository,
            TeacherRepository teacherRepository,
            ClassPlansMapper classPlansMapper,
            // A anotação @Qualifier vai diretamente no parâmetro para resolver a
            // ambiguidade.
            @Qualifier("englishActivityService") ActivityGeneratorService activityGenerator) {
        this.classPlansRepository = classPlansRepository;
        this.teacherRepository = teacherRepository;
        this.classPlansMapper = classPlansMapper;
        this.activityGenerator = activityGenerator;
    }

    @Override
    public JpaRepository<ClassPlansEntity, Long> getRepository() {
        return classPlansRepository;
    }

    @Override
    public ClassPlansMapper getMapper() {
        return classPlansMapper;
    }

    @Override
    public JpaRepository<TeacherEntity, Long> getOwnerRepository() {
        return teacherRepository;
    }

    @Override
    @Transactional
    public ClassPlansResponseDTO create(ClassPlansCreateDTO data, Long ownerId) {
        TeacherEntity teacher = teacherRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));

        LocalDateTime classDate = data.classDate();
        LocalDateTime start = classDate.minusMinutes(30);
        LocalDateTime end = classDate.plusMinutes(30);

        if (classPlansRepository.countConflictingPlans(ownerId, start, end) > 0) {
            throw new ConflictException("Já existe uma aula marcada nesse intervalo de 30 minutos.");
        }

        String generatedContent = activityGenerator.generateSession(data.inputData());

        ClassPlansEntity entity = new ClassPlansEntity(data.title(), data.inputData(), data.classDate(), teacher,
                generatedContent);
        ClassPlansEntity classPlan = classPlansRepository.save(entity);
        return classPlansMapper.toResponse(classPlan);
    }

    @Override
    public List<ClassPlansResponseDTO> findAll(Long ownerId) {
        List<ClassPlansEntity> classPlans = classPlansRepository.findAllByOwnerId(ownerId);
        return classPlans.stream().map(classPlansMapper::toResponse).toList();
    }

}
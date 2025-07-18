package com.neo_educ.backend.apps.exercises.workout.service;

import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.personal.repository.PersonalRepository;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutCreateDTO;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutResponseDTO;
import com.neo_educ.backend.apps.exercises.workout.entity.WorkoutEntity;
import com.neo_educ.backend.apps.exercises.workout.mappers.WorkoutMapper;
import com.neo_educ.backend.apps.exercises.workout.repository.WorkoutRepository;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.core.service.SessionService;
import com.neo_educ.backend.exceptions.ConflictException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService
        implements SessionService<WorkoutEntity, WorkoutCreateDTO, WorkoutResponseDTO, PersonalEntity> {

    private final WorkoutRepository workoutRepository;
    private final PersonalRepository personalRepository;
    private final WorkoutMapper workoutMapper;
    private final ActivityGeneratorService activityGenerator;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            PersonalRepository personalRepository,
            WorkoutMapper workoutMapper,
            @Qualifier("exercisesActivityService") ActivityGeneratorService activityGenerator) {
        this.workoutRepository = workoutRepository;
        this.personalRepository = personalRepository;
        this.workoutMapper = workoutMapper;
        this.activityGenerator = activityGenerator;
    }

    @Override
    public JpaRepository<WorkoutEntity, Long> getRepository() {
        return this.workoutRepository;
    }

    @Override
    public WorkoutMapper getMapper() {
        return this.workoutMapper;
    }

    @Override
    public JpaRepository<PersonalEntity, Long> getOwnerRepository() {
        return this.personalRepository;
    }

    @Override
    public List<WorkoutResponseDTO> findAll(Long ownerId) {
        return workoutRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(workoutMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WorkoutResponseDTO create(WorkoutCreateDTO data, Long ownerId) {
        PersonalEntity personal = personalRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Personal não encontrado"));

        LocalDateTime classDate = data.classDate();
        LocalDateTime start = classDate.minusMinutes(30);
        LocalDateTime end = classDate.plusMinutes(30);

        if (workoutRepository.countConflictingPlans(ownerId, start, end) > 0) {
            throw new ConflictException("Já existe um treino marcado nesse intervalo de 30 minutos.");
        }

        String generatedContent = activityGenerator.generateSession(data.inputData());

        WorkoutEntity entity = WorkoutEntity.builder()
                .title(data.title())
                .topic(data.inputData())
                .date(data.classDate())
                .goal(data.goal()) 
                .duration(data.duration()) 
                .workoutsPerWeek(data.workoutsPerWeek()) 
                .owner(personal)
                .content(generatedContent)
                .build();

        WorkoutEntity savedWorkout = workoutRepository.save(entity);
        return workoutMapper.toResponse(savedWorkout);
    }
}
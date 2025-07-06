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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService implements SessionService<WorkoutEntity, WorkoutCreateDTO, WorkoutResponseDTO> {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private WorkoutMapper workoutMapper;

    @Autowired
    @Qualifier("exercisesActivityService")
    private ActivityGeneratorService activityGenerator;

    public WorkoutResponseDTO create(WorkoutCreateDTO data, Long personalID) {

        PersonalEntity personal = personalRepository.findById(personalID).orElseThrow(() -> new EntityNotFoundException("Personal não encontrado"));

        LocalDateTime classDate = data.classDate();
        LocalDateTime start = classDate.minusMinutes(30);
        LocalDateTime end = classDate.plusMinutes(30);

        Long conflicts = workoutRepository.countConflictingPlans(personalID, start, end);

        if (conflicts > 0) {
            throw new ConflictException("Já existe um treino marcada nesse intervalo de 30 minutos.");
        }
        String generatedContent = activityGenerator.generateSession(data.inputData());

        WorkoutEntity entity = new WorkoutEntity(data.title(),
                data.inputData(),
                data.classDate(),
                personal,
                generatedContent);
        WorkoutEntity classPlan = workoutRepository.save(entity);
        return workoutMapper.toResponse(classPlan);

    }


    public WorkoutResponseDTO findByID(Long id) {

        Optional<WorkoutEntity> optionalClassPlan = workoutRepository.findById(id);

        if (optionalClassPlan.isEmpty()) {
            throw new EntityNotFoundException("Treino não encontrado com o ID: " + id);
        }

        return workoutMapper.toResponse(optionalClassPlan.get());

    }

    public List<WorkoutResponseDTO> findAll(Long personalID) {

        List<WorkoutEntity> classPlans = workoutRepository.findAllByPersonalId(personalID);
        return classPlans.stream().map(workoutMapper::toResponse).toList();

    }

    public void delete(Long id) {
        this.workoutRepository.deleteById(id);
    }

    public WorkoutResponseDTO patchAiGeneratedContent(Long id, String input) {
        WorkoutEntity entity = workoutRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Treino não encontrado com o ID: " + id));
        entity.setContent(input);
        WorkoutEntity classPlan = workoutRepository.save(entity);
        return workoutMapper.toResponse(classPlan);
    }

}

package com.neo_educ.backend.apps.exercises.workout.controllers;

import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutCreateDTO;
import com.neo_educ.backend.apps.exercises.workout.dto.WorkoutResponseDTO;
import com.neo_educ.backend.core.controller.AbstractSessionController;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workouts") 
public class WorkoutController extends AbstractSessionController<
        WorkoutCreateDTO,
        WorkoutResponseDTO,
        PersonalEntity
> {
    public WorkoutController(@Qualifier("exercisesFactory") ApplicationFactory appFactory) {
        super(appFactory);
    }
}
package com.neo_educ.backend.apps.exercises.factory;

import com.neo_educ.backend.apps.exercises.athlete.service.AthleteService;
import com.neo_educ.backend.apps.exercises.personal.service.PersonalService;
import com.neo_educ.backend.apps.exercises.workout.service.WorkoutService;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component("exercisesFactory")
public class ExercicesApplicationFactory implements ApplicationFactory {

    private final PersonalService personalService;
    private final PasswordEncoder passwordEncoder;
    private final ActivityGeneratorService activityGeneratorService;
    private final WorkoutService workoutService;
    private final AthleteService athleteService;

    public ExercicesApplicationFactory(
            PersonalService personalService,
            PasswordEncoder passwordEncoder,
            @Qualifier("exercisesActivityService") ActivityGeneratorService activityGeneratorService,
            AthleteService athleteService,
            WorkoutService workoutService) {
        this.personalService = personalService;
        this.passwordEncoder = passwordEncoder;
        this.activityGeneratorService = activityGeneratorService;
        this.workoutService = workoutService;
        this.athleteService=athleteService;
    }

    @Override
    public AuthService createAuthService() {
        return new AuthService(this.personalService, this.passwordEncoder);
    }

    @Override
    public UserService createUserService() {
        return this.personalService;
    }

    @Override
    public ActivityGeneratorService createActivityGeneratorService() {
        return this.activityGeneratorService;
    }

    @Override
    public SessionService<?, ?, ?> createSessionService() {
        return this.workoutService;
    }

    @Override
    public ClientService<?, ?, ?> createClientService() {
        return this.athleteService;
    }
}
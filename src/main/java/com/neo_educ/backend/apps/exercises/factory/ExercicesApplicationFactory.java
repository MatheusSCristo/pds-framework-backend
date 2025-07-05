package com.neo_educ.backend.apps.exercises.factory;

import com.neo_educ.backend.apps.exercises.athlete.service.AthleteService;
import com.neo_educ.backend.apps.exercises.personal.service.PersonalService;
import com.neo_educ.backend.apps.exercises.workout.service.WorkoutPlanService;
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
    private final AthleteService athleteService;
    private final WorkoutPlanService workoutPlanService;
    private final AuthService authService;

    public ExercicesApplicationFactory(
            PersonalService personalService,
            PasswordEncoder passwordEncoder,
            @Qualifier("exercisesActivityService") ActivityGeneratorService activityGeneratorService,
            AthleteService athleteService,
            WorkoutPlanService workoutPlanService,
            AuthService authService) {
        this.personalService = personalService;
        this.passwordEncoder = passwordEncoder;
        this.activityGeneratorService = activityGeneratorService;
        this.workoutPlanService = workoutPlanService;
        this.athleteService = athleteService;
        this.authService = authService;
    }

    @Override
    public AuthService createAuthService() {
        return this.authService;
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
        return this.workoutPlanService;
    }

    @Override
    public ClientService<?, ?, ?> createClientService() {
        return this.athleteService;
    }
}
package com.neo_educ.backend.apps.nutrition.factory;

import com.neo_educ.backend.apps.nutrition.consultation.service.NutritionalConsultationService;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.service.NutritionistService;
import com.neo_educ.backend.apps.nutrition.patient.service.PatientService;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.core.service.AuthService;
import com.neo_educ.backend.core.service.ClientService;
import com.neo_educ.backend.core.service.SessionService;
import com.neo_educ.backend.core.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component("nutritionFactory")
public class NutritionApplicationFactory implements ApplicationFactory {

    private final NutritionistService nutritionistService;
    private final PasswordEncoder passwordEncoder;
    private final ActivityGeneratorService activityGeneratorService;
    private final NutritionalConsultationService nutritionalConsultationService;
    private final PatientService patientService;

    public NutritionApplicationFactory(
            NutritionistService nutritionistService,
            PasswordEncoder passwordEncoder,
            @Qualifier("nutritionActivityService") ActivityGeneratorService activityGeneratorService,
            NutritionalConsultationService nutritionalConsultationService,
            PatientService patientService
    ) {
        this.nutritionistService = nutritionistService;
        this.passwordEncoder = passwordEncoder;
        this.activityGeneratorService = activityGeneratorService;
        this.nutritionalConsultationService = nutritionalConsultationService;
        this.patientService = patientService;
    }

    @Override
    public AuthService<NutritionistEntity> createAuthService() {
        return new AuthService<NutritionistEntity>(this.nutritionistService, this.passwordEncoder);
    }

    @Override
    public UserService<NutritionistEntity> createUserService() {
        return this.nutritionistService;
    }

    @Override
    public ActivityGeneratorService createActivityGeneratorService() {
        return this.activityGeneratorService;
    }

    @Override
    public SessionService<?, ?, ?> createSessionService() {
        return this.nutritionalConsultationService;
    }

    @Override
    public ClientService<?, ?, ?> createClientService() {
        return this.patientService;
    }
}
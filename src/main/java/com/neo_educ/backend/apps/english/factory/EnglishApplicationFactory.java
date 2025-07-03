package com.neo_educ.backend.apps.english.factory;

import com.neo_educ.backend.apps.english.classplans.service.ClassPlansService;
import com.neo_educ.backend.apps.english.teacher.service.TeacherService;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.core.service.AuthService;
import com.neo_educ.backend.core.service.SessionService;
import com.neo_educ.backend.core.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component("englishFactory")
public class EnglishApplicationFactory implements ApplicationFactory {

    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;
    private final ActivityGeneratorService activityGeneratorService;
    private final ClassPlansService classPlansService;

    public EnglishApplicationFactory(
            TeacherService teacherService,
            PasswordEncoder passwordEncoder,
            @Qualifier("englishActivityService") ActivityGeneratorService activityGeneratorService,
            ClassPlansService classPlansService
    ) {
        this.teacherService = teacherService;
        this.passwordEncoder = passwordEncoder;
        this.activityGeneratorService = activityGeneratorService;
        this.classPlansService = classPlansService;
    }

    @Override
    public AuthService createAuthService() {
        return new AuthService(this.teacherService, this.passwordEncoder);
    }

    @Override
    public UserService createUserService() {
        return this.teacherService;
    }

    @Override
    public ActivityGeneratorService createActivityGeneratorService() {
        return this.activityGeneratorService;
    }

    @Override
    public SessionService<?, ?, ?> createSessionService() {
        return this.classPlansService;
    }
}
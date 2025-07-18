package com.neo_educ.backend.apps.english.factory;

import com.neo_educ.backend.apps.english.classplans.service.ClassPlansService;
import com.neo_educ.backend.apps.english.student.service.StudentService;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.service.TeacherService;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.core.service.AuthService;
import com.neo_educ.backend.core.service.ClientService;
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
    private final StudentService studentService;

    public EnglishApplicationFactory(
            TeacherService teacherService,
            PasswordEncoder passwordEncoder,
            @Qualifier("englishActivityService") ActivityGeneratorService activityGeneratorService,
            ClassPlansService classPlansService,
            StudentService studentService
    ) {
        this.teacherService = teacherService;
        this.passwordEncoder = passwordEncoder;
        this.activityGeneratorService = activityGeneratorService;
        this.classPlansService = classPlansService;
        this.studentService = studentService;
    }

    @Override
    public AuthService<TeacherEntity> createAuthService() {
        return new AuthService<TeacherEntity>(this.teacherService, this.passwordEncoder);
    }

    @Override
    public UserService<TeacherEntity> createUserService() {
        return this.teacherService;
    }

    @Override
    public ActivityGeneratorService createActivityGeneratorService() {
        return this.activityGeneratorService;
    }

    @Override
    public SessionService<?, ?, ?, ?> createSessionService() {
        return this.classPlansService;
    }

    @Override
    public ClientService<?, ?, ?, ?> createClientService() {
        return this.studentService;
    }
}
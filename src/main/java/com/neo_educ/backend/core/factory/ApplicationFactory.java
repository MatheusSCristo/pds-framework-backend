package com.neo_educ.backend.core.factory;

import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.modules.auth.service.AuthService;

public interface ApplicationFactory {

    AuthService createAuthService();
    UserService createUserService();
    ActivityGeneratorService createActivityGeneratorService();

}

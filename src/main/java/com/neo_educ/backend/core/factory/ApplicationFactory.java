package com.neo_educ.backend.core.factory;


import com.neo_educ.backend.core.service.*;


public interface ApplicationFactory {

        AuthService<?> createAuthService();
        UserService<?> createUserService();
    SessionService<?, ?, ?, ?> createSessionService();
    ActivityGeneratorService createActivityGeneratorService();
    ClientService<?, ?, ?, ?> createClientService();

}

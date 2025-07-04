package com.neo_educ.backend.core.factory;


import com.neo_educ.backend.core.service.ActivityGeneratorService;
import com.neo_educ.backend.core.service.AuthService;
import com.neo_educ.backend.core.service.ClientService;
import com.neo_educ.backend.core.service.SessionService;
import com.neo_educ.backend.core.service.UserService;


public interface ApplicationFactory {

    AuthService createAuthService();
    UserService createUserService();

    /**
     * Cria uma instância do serviço de geração de atividades com IA,
     * específico para o contexto da aplicação.
     * @return Uma implementação de ActivityGeneratorService.
     */
    ActivityGeneratorService createActivityGeneratorService();

    /**
     * Cria uma instância do serviço de sessão específico da aplicação.
     * @return Uma implementação de SessionService.
     */
    SessionService<?, ?, ?> createSessionService(); 

    /**
     * Cria uma instância do serviço de cliente específico da aplicação.
     * @return Uma implementação de ClientService.
     */
    ClientService<?, ?, ?> createClientService();

}

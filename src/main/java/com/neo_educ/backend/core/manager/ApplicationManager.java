package com.neo_educ.backend.core.manager;

import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class ApplicationManager {

    private Map<String, ApplicationFactory> factories;

    @Autowired
    public ApplicationManager(Map<String, ApplicationFactory> factories) {
        this.factories = factories;
    }

    public ApplicationFactory getFactory(String applicationType) {
        String factory = applicationType.toLowerCase() + "ApplicationFactory";

        return Optional.ofNullable(factories.get(factory))
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma aplicação encontrada para o tipo: " + applicationType));
    }

}

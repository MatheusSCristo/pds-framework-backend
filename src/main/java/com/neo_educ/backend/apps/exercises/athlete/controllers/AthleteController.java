package com.neo_educ.backend.apps.exercises.athlete.controllers;

import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteRegisterDTO;
import com.neo_educ.backend.apps.exercises.athlete.dto.AthleteResponseDTO;
import com.neo_educ.backend.core.controller.AbstractClientController;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises/athlete")
public class AthleteController extends AbstractClientController<AthleteRegisterDTO, AthleteResponseDTO> {
    public AthleteController(@Qualifier("exercisesFactory") ApplicationFactory appFactory) {
        super(appFactory);
    }
}
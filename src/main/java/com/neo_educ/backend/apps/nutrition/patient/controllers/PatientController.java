package com.neo_educ.backend.apps.nutrition.patient.controllers;

import com.neo_educ.backend.apps.nutrition.patient.dto.PatientRegisterDTO;
import com.neo_educ.backend.apps.nutrition.patient.dto.PatientResponseDTO;
import com.neo_educ.backend.core.controller.AbstractClientController;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController extends AbstractClientController<PatientRegisterDTO, PatientResponseDTO> {
    public PatientController(@Qualifier("nutritionFactory") ApplicationFactory appFactory) {
        super(appFactory);
    }
}
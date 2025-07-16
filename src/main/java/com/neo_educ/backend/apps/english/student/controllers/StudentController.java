package com.neo_educ.backend.apps.english.student.controllers;

import com.neo_educ.backend.apps.english.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.core.controller.AbstractClientController;
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController extends AbstractClientController<StudentRegisterDTO, StudentResponseDTO> {

    public StudentController(@Qualifier("englishFactory") ApplicationFactory appFactory) {
        super(appFactory);
    }
}
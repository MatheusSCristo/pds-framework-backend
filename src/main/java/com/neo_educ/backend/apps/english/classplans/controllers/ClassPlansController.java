package com.neo_educ.backend.apps.english.classplans.controllers;

import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.apps.english.classplans.dto.ClassPlansResponseDTO;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.controller.AbstractSessionController; 
import com.neo_educ.backend.core.factory.ApplicationFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class-plans")
public class ClassPlansController extends AbstractSessionController<
        ClassPlansCreateDTO,
        ClassPlansResponseDTO,
        TeacherEntity
> {
        public ClassPlansController(@Qualifier("englishFactory") ApplicationFactory appFactory) {
        super(appFactory);
    }
}
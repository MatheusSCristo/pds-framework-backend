package com.neo_educ.backend.apps.exercises.personal.controller;

import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.apps.exercises.personal.mappers.PersonalMapper;
import com.neo_educ.backend.core.controller.UserController;
import com.neo_educ.backend.core.dto.user.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises/personal")
public class PersonalController extends UserController<PersonalEntity, UserResponseDTO> {

    @Autowired
    private PersonalMapper personalMapper;

    @Override
    protected UserResponseDTO mapToDto(PersonalEntity entity) {
        return personalMapper.toDTO(entity);
    }

    public ResponseEntity<UserResponseDTO> authenticatedUser() {
        return super.authenticatedUser();
    }
}

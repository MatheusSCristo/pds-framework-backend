package com.neo_educ.backend.apps.exercises.personal.mappers;

import com.neo_educ.backend.apps.english.auth.dto.RegisterDTO;
import com.neo_educ.backend.apps.exercises.personal.entity.PersonalEntity;
import com.neo_educ.backend.core.dto.user.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonalMapper {

    UserResponseDTO toDTO(PersonalEntity personalEntity);

    PersonalEntity toEntity(RegisterDTO registerDTO);
}

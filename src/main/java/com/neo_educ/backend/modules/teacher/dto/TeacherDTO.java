package com.neo_educ.backend.modules.teacher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TeacherDTO(
        String name,
        @JsonProperty("last_name")
        String lastName,
        String email,
        String phone
) {
}

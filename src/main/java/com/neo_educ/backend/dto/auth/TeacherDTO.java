package com.neo_educ.backend.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TeacherDTO(
        String name,
        @JsonProperty("last_name")
        String lastName,
        String email,
        String city,
        String country,
        String state
) {
}

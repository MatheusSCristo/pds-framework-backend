package com.neo_educ.backend.apps.english.teacher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TeacherResponseDTO(
        String name,
        @JsonProperty("last_name")
        String lastName,
        String email,
        String phone,
        @JsonProperty("invite_token")
        String inviteToken
) {
}

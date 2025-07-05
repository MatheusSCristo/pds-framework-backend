package com.neo_educ.backend.core.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDTO(
        String name,
        @JsonProperty("last_name")
        String lastName,
        String email,
        String phone,
        @JsonProperty("invite_token")
        String inviteToken
) {
}

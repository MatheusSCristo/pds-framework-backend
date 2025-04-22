package com.neo_educ.backend.modules.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterDTO(
        @JsonProperty("name")
     String name,
     @JsonProperty("last_name")
     String lastName,
     @JsonProperty("email")
     String email,
     @JsonProperty("password")
     String password,
     @JsonProperty("phone")
     String phone
){}

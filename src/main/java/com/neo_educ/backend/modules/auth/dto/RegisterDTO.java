package com.neo_educ.backend.modules.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank(message = "Nome do usuário deve ser informado")
        @JsonProperty("name")
        String name,
        @NotBlank(message = "Sobrenome do usuário deve ser informado")
        String lastName,
        @NotBlank(message = "Email do usuário deve ser informado")
        @JsonProperty("email")
        String email,
        @NotBlank(message = "Senha do usuário deve ser informado")
        @JsonProperty("password")
        String password,
        @NotBlank(message = "Telefone do usuário deve ser informado")
        @JsonProperty("phone")
        String phone
) {
}
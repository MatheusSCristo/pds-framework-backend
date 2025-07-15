package com.neo_educ.backend.apps.nutrition.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PatientRegisterDTO(

    @NotBlank(message = "O nome é obrigatório.")
    String name,

    @NotBlank(message = "O sobrenome é obrigatório.")
    String lastName,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String email,

    @NotBlank(message = "O telefone é obrigatório.")
    String phone,

    @NotBlank(message = "O campo de alergias é obrigatório. Se não houver, escreva 'Nenhuma'.")
    String allergies,

    @NotNull(message = "As metas nutricionais são obrigatórias.")
    @Size(min = 1, message = "Selecione pelo menos uma meta.")
    List<@NotNull(message = "O código da meta não pode ser nulo.") Integer> nutritionalGoals,

    @NotBlank(message = "O token de convite é obrigatório.")
    String token
) {
}
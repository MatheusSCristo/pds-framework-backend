package com.neo_educ.backend.apps.nutrition.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Set;

import com.neo_educ.backend.apps.nutrition.patient.enums.NutritionalGoal;

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

    Set<NutritionalGoal> nutritionalGoals,

    @NotBlank(message = "O token de convite é obrigatório.")
    String token
) {
}
package com.neo_educ.backend.apps.nutrition.patient.enums;

import lombok.Getter;

@Getter
public enum NutritionalGoal {
    PERDA_DE_PESO(1, "Perda de Peso"),
    GANHO_DE_PESO(2, "Ganho de Peso"),
    GANHO_DE_MASSA_MUSCULAR(3, "Ganho de Massa Muscular"),
    MANUTENCAO_DE_PESO(4, "Manutenção de Peso"),
    MELHORA_DA_SAUDE(5, "Melhora da Saúde");

    private final int code;
    private final String descricao;

    NutritionalGoal(int code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }
}
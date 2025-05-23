package com.neo_educ.backend.modules.student.enums;

import lombok.Getter;

@Getter
public enum InterestsEnum {
    ENTRETERIMENTO(1),
    DESENVOLVIMENTO_PESSOAL(2),
    EDUCACAO_CARREIRA(3),
    ESTILO_VIDA_CULTURA(4),
    HOBBIES_INTERESSES_PESSOAIS(5);

    private final int code;

    InterestsEnum(int code) {
        this.code = code;
    }

    public static InterestsEnum fromCode(int code) {
        for (InterestsEnum interest : values()) {
            if (interest.getCode() == code) {
                return interest;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}

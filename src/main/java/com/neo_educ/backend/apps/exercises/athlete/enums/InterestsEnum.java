package com.neo_educ.backend.apps.exercises.athlete.enums;

import lombok.Getter;

@Getter
public enum InterestsEnum {
    MUSCLE_GAIN(1),
    WEIGHT_LOSS(2),
    RESISTENCE(3);

    private final int code;

    InterestsEnum(int code) {
        this.code = code;
    }
}

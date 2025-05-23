package com.neo_educ.backend.exceptions.generateMaterial;

public class ActivityGenerateException extends RuntimeException {
    public ActivityGenerateException() {
        super("Erro ao gerar conteúdo");
    }
}

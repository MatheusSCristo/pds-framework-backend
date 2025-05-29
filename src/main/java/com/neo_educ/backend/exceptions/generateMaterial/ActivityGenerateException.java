package com.neo_educ.backend.exceptions.generateMaterial;

import com.neo_educ.backend.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class ActivityGenerateException extends ApiException {
    public ActivityGenerateException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar conteúdo");
    }
}

package com.neo_educ.backend.exceptions.generateMaterial;

public class TopicNullException extends RuntimeException {
    public TopicNullException() {
        super("O campo [tópico] não pode ser nulo");
    }
}

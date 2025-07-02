package com.neo_educ.backend.core.model;

import jakarta.persistence.*;
        import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public abstract class AbstractNoteEntity extends AbstractModel {

    @NotBlank(message = "O campo [title] não pode ser vazio")
    protected String title;

    @NotBlank(message = "O campo [content] não pode ser vazio")
    protected String content;

}


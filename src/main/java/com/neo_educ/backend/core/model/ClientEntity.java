package com.neo_educ.backend.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ClientEntity extends AbstractModel {

    @NotBlank
    protected String name;

    @Column(name = "last_name")
    protected String lastName;

    protected String email;

    @NotBlank
    protected String phone;

}


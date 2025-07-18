package com.neo_educ.backend.core.model;

import jakarta.persistence.*; 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class ClientEntity<U extends AbstractModel> extends AbstractModel {

    
    @NotBlank(message = "O nome não pode estar em branco.")
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "last_name")
    protected String lastName;

    @Email(message = "O formato do e-mail é inválido.")
    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @NotBlank(message = "O telefone não pode estar em branco.")
    @Column(name = "phone", nullable = false)
    protected String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    protected U owner;
}
package com.neo_educ.backend.apps.nutrition.nutritionist.entity;

import com.neo_educ.backend.apps.nutrition.consultation.entity.NutritionalConsultationEntity;
import com.neo_educ.backend.apps.nutrition.patient.entity.PatientEntity;
import com.neo_educ.backend.core.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "nutritionists")
@Entity(name = "nutritionist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NutritionistEntity extends UserEntity {

    private String specialty;

    @Column(name="invite_token")
    private String inviteToken;

    // Relação com as consultas que o nutricionista realiza
    @Builder.Default
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NutritionalConsultationEntity> consultations = new ArrayList<>();

    // Relação com os pacientes do nutricionista
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientEntity> patients;

    public NutritionistEntity(String name, String lastName, String email, String password, String phone, String specialty) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.specialty = specialty;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password; // Retorna a senha herdada de UserEntity
    }

    @Override
    public String getUsername() {
        return this.email; // Usa o email como nome de usuário para login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // A conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // A conta nunca é bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // A conta está sempre habilitada
    }
}
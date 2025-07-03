package com.neo_educ.backend.apps.english.teacher.entity;

import com.neo_educ.backend.apps.english.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.apps.english.notes.entity.NotesEntity;
import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.core.model.AbstractModel;
import com.neo_educ.backend.core.model.UserEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "teachers")
@Entity(name="teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TeacherEntity extends UserEntity implements UserDetails {

    @Column(name="invite_token")
    private String inviteToken;
    @Builder.Default
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassPlansEntity> classPlans = new ArrayList<>();
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private List<StudentEntity> students;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.neo_educ.backend.apps.exercises.personal.entity;

import com.neo_educ.backend.apps.english.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.core.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Table(name = "personal")
@Entity(name="personal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonalEntity extends UserEntity implements UserDetails {

    @Column(name="invite_token")
    private String inviteToken;
    @Builder.Default
    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassPlansEntity> classPlans = new ArrayList<>();
    @OneToMany(mappedBy = "personal", cascade = CascadeType.REMOVE)
    private List<StudentEntity> students;

    public Long getId(){
        return id;
    }


    public PersonalEntity(String name,String lastName,String email,String password,String phone){
        this.name=name;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.phone=phone;
    }

}

package com.neo_educ.backend.apps.exercises.personal.entity;

import com.neo_educ.backend.apps.exercises.athlete.entity.AthleteEntity;
import com.neo_educ.backend.apps.exercises.workout.entity.WorkoutEntity;
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
    private List<WorkoutEntity> workoutPlans = new ArrayList<>();
    @OneToMany(mappedBy = "personal", cascade = CascadeType.REMOVE)
    private List<AthleteEntity> athletes;

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

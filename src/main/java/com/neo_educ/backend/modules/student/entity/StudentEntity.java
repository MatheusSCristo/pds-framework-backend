package com.neo_educ.backend.modules.student.entity;

import com.neo_educ.backend.core.model.ClientEntity;
import com.neo_educ.backend.modules.student.enums.InterestsEnum;
import com.neo_educ.backend.modules.student.enums.ProficiencyLevel;
import com.neo_educ.backend.modules.studentActivity.entity.StudentActivityEntity;
import com.neo_educ.backend.modules.notes.entity.NotesEntity;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class StudentEntity extends ClientEntity {

    @NotNull
    @ElementCollection(targetClass = InterestsEnum.class)
    @CollectionTable(name = "interests", joinColumns = @JoinColumn(name = "student_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "interest")
    private List<InterestsEnum> interests;

    private ProficiencyLevel proficiencyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeacherEntity  teacher;


    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<NotesEntity> notes;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<StudentActivityEntity> activities;
}

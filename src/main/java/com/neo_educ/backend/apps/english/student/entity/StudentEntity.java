package com.neo_educ.backend.apps.english.student.entity;

import com.neo_educ.backend.apps.english.notes.entity.NotesEntity;
import com.neo_educ.backend.apps.english.student.enums.InterestsEnum;
import com.neo_educ.backend.apps.english.student.enums.ProficiencyLevel;
import com.neo_educ.backend.apps.english.studentActivity.entity.StudentActivityEntity;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.core.model.ClientEntity;

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
    @CollectionTable(name = "student_interests", joinColumns = @JoinColumn(name = "student_id"))
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

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public List<StudentActivityEntity> getActivities() {
        return activities;
    }
}

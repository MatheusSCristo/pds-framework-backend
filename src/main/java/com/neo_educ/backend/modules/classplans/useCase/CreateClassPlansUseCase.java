package com.neo_educ.backend.modules.classplans.useCase;

import com.neo_educ.backend.modules.classplans.dto.ClassPlansCreateDTO;
import com.neo_educ.backend.modules.classplans.entity.ClassPlansEntity;
import com.neo_educ.backend.modules.classplans.enums.ClassPlanStatus;
import com.neo_educ.backend.modules.classplans.repository.ClassPlansRepository;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateClassPlansUseCase {

    private final ClassPlansRepository classPlansRepository;
    private final TeacherRepository teacherRepository;

    public CreateClassPlansUseCase(ClassPlansRepository classPlansRepository, TeacherRepository teacherRepository) {
        this.classPlansRepository = classPlansRepository;
        this.teacherRepository = teacherRepository;
    }

    public ClassPlansEntity execute(ClassPlansCreateDTO data) {
        Optional<TeacherEntity> optionalTeacher = teacherRepository.findByEmail(data.teacher_email());

        if (optionalTeacher.isEmpty()) {
            throw new RuntimeException("Professor não encontrado com email: " + data.teacher_email());
        }

        TeacherEntity teacher = optionalTeacher.get();

        ClassPlansEntity entity = new ClassPlansEntity();
        entity.setTopic(data.topic());
        entity.setClassDate(data.classDate());
        entity.setInputData(data.inputData());
        entity.setTeacher(teacher);
        entity.setStatus(ClassPlanStatus.PENDING);

        return classPlansRepository.save(entity);
    }
}

package com.neo_educ.backend.apps.english.student.service;

import com.neo_educ.backend.apps.english.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.apps.english.student.dto.StudentResponseDTO;
import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.apps.english.student.mapper.StudentMapper;
import com.neo_educ.backend.apps.english.student.repository.StudentRepository;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.repository.TeacherRepository;
import com.neo_educ.backend.core.service.ClientService;
import com.neo_educ.backend.exceptions.student.StudentAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class StudentService implements ClientService<StudentEntity, StudentRegisterDTO, StudentResponseDTO, TeacherEntity> {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public StudentRepository getRepository() {
        return this.studentRepository;
    }

    @Override
    public StudentMapper getModelMapper() {
        return this.studentMapper;
    }

    @Override
    public JpaRepository<TeacherEntity, Long> getOwnerRepository() {
        return this.teacherRepository;
    }
    
    @Override
    @Transactional
    public StudentResponseDTO create(StudentRegisterDTO createDto, Long userId) {
        TeacherEntity teacher = teacherRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado com ID: " + userId));

        studentRepository.findByEmailAndTeacher(createDto.email(), teacher).ifPresent(s -> {
            throw new StudentAlreadyExistsException();
        });

        StudentEntity entity = getModelMapper().toEntity(createDto);
        entity.setOwner(teacher);

        StudentEntity savedStudent = getRepository().save(entity);
        return getModelMapper().toResponse(savedStudent);
    }
    
    @Override
    public List<StudentResponseDTO> findAll(Long ownerId) {
        List<StudentEntity> entities = studentRepository.findAllByTeacherId(ownerId);
        return entities.stream().map(studentMapper::toResponse).toList();
    }
}
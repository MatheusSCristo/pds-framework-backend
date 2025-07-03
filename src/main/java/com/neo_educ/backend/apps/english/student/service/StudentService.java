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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service

public class StudentService implements ClientService<StudentEntity, StudentRegisterDTO, StudentResponseDTO> {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    @Transactional
    public StudentResponseDTO create(StudentRegisterDTO createDto, Long teacherId) {
        TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));

        if (studentRepository.findByEmailAndTeacher(createDto.email(), teacher).isPresent()) {
            throw new StudentAlreadyExistsException();
        }

        StudentEntity entity = studentMapper.toEntity(createDto);
        entity.setTeacher(teacher);

        StudentEntity savedStudent = studentRepository.save(entity);
        return studentMapper.toResponseDTO(savedStudent);
    }

    @Override
    public StudentResponseDTO findById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com ID: " + id + " não encontrado"));
    }

    @Override
    public List<StudentResponseDTO> findAll(Long teacherId) {
        List<StudentEntity> entities = studentRepository.findAllByTeacherId(teacherId);
        return entities.stream().map(studentMapper::toResponseDTO).toList();
    }

    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Estudante com ID: " + id + " não encontrado para deleção.");
        }
        studentRepository.deleteById(id);
    }
}
package com.neo_educ.backend.modules.student.service;

import com.neo_educ.backend.exceptions.student.*;
import com.neo_educ.backend.modules.student.enums.InterestsEnum;
import com.neo_educ.backend.modules.student.dto.StudentRegisterDTO;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.mapper.StudentMapper;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherService teacherService;


    public List<StudentResponseDTO> getTeacherStudents(String email) {
        List<StudentEntity> entities = studentRepository.findAllByTeacher_Email(email);
        return entities.stream().map(item -> studentMapper.toResponseDTO(item)).toList();
    }


    public void createStudent(StudentRegisterDTO studentRegisterDto) {
        TeacherEntity teacher = teacherService.findTeacherByToken(studentRegisterDto.token());
        if (studentRepository.findByEmailAndTeacher(studentRegisterDto.email(), teacher).isPresent()) {
            throw new StudentAlreadyExistsException();
        }
        List<InterestsEnum> interestsEnums = studentRegisterDto.interests().stream().map(InterestsEnum::fromCode).toList();
        StudentEntity entity = studentMapper.toEntity(studentRegisterDto);
        entity.setTeacher(teacher);
        entity.setInterests(interestsEnums);
        studentRepository.save(entity);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public StudentEntity findStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com ID: " + studentId + " não encontrado"));

    }

    public StudentResponseDTO findStudentDTO(Long studentId) {
        StudentEntity entity = findStudent(studentId);
        return studentMapper.toResponseDTO(entity);
    }


}

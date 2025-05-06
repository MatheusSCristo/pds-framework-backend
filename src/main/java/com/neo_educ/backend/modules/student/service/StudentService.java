package com.neo_educ.backend.modules.student.service;

import com.neo_educ.backend.exceptions.student.*;
import com.neo_educ.backend.modules.interests.enums.InterestsEnum;
import com.neo_educ.backend.modules.student.dto.StudentRequestDTO;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.mapper.StudentMapper;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.teacher.service.TeacherService;
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


    public void createStudent(StudentRequestDTO studentRequestDTO) {
        TeacherEntity teacher = teacherService.findTeacherByToken(studentRequestDTO.token());
        if (studentRepository.findByEmailAndTeacher(studentRequestDTO.email(), teacher).isPresent()) {
            throw new StudentAlreadyExistsException();
        }
        List<InterestsEnum> interestsEnums = studentRequestDTO.interests().stream().map(InterestsEnum::fromCode).toList();
        StudentEntity entity = studentMapper.toEntity(studentRequestDTO);
        entity.setTeacher(teacher);
        entity.setInterests(interestsEnums);
        studentRepository.save(entity);
    }

    public void deleteStudent(Long studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        studentRepository.delete(student);
    }

    public StudentResponseDTO findStudent(Long studentId) {
        StudentEntity entity = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        return studentMapper.toResponseDTO(entity);
    }

    public StudentResponseDTO updateStudent(StudentRequestDTO studentRequestDTO, Long studentId) {
        if (studentRequestDTO.name() == null && studentRequestDTO.email() == null && studentRequestDTO.proficiencyLevel() == null) {
            throw new InvalidUpdateStudentInformationsRequestException();
        }

        StudentEntity student = this.studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        if (studentRequestDTO.name() != null) {
            if (studentRequestDTO.name().equals(student.getName())) {
                throw new EqualStudentNameException();
            }

            student.setName(studentRequestDTO.name());
        }

        if (studentRequestDTO.email() != null) {
            if (studentRequestDTO.email().equals(student.getEmail())) {
                throw new EqualStudentEmailException();
            }

            student.setEmail(studentRequestDTO.email());
        }

        if (studentRequestDTO.proficiencyLevel() != null) {
            if (studentRequestDTO.proficiencyLevel().equals(student.getProficiencyLevel())) {
                throw new EqualProficiencyLevelException();
            }

            student.setProficiencyLevel(studentRequestDTO.proficiencyLevel());
        }

        student.setUpdatedAt(LocalDateTime.now());

        StudentEntity updatedStudentInformation = this.studentRepository.save(student);

        return studentMapper.toResponseDTO(updatedStudentInformation);
    }

}

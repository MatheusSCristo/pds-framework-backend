package com.neo_educ.backend.modules.student.useCase;

import com.neo_educ.backend.exceptions.student.*;
import com.neo_educ.backend.modules.student.dto.StudentRequestDTO;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UpdateStudentInformationsUseCase {

  @Autowired
  private StudentRepository studentRepository;

  public StudentResponseDTO execute(StudentRequestDTO studentRequestDTO, Long studentId, String teacherEmail) {
    if(studentRequestDTO.name() == null && studentRequestDTO.email() == null && studentRequestDTO.proficiencyLevel() == null) {
      throw new InvalidUpdateStudentInformationsRequestException();
    }

    StudentEntity student = this.studentRepository.findByIdAndTeacherEmail(studentId, teacherEmail)
        .orElseThrow(StudentNotFoundException::new);

    if(studentRequestDTO.name() != null) {
      if(studentRequestDTO.name().equals(student.getName())) {
        throw new EqualStudentNameException();
      }

      student.setName(studentRequestDTO.name());
    }

    if(studentRequestDTO.email() != null) {
      if(studentRequestDTO.email().equals(student.getEmail())) {
        throw new EqualStudentEmailException();
      }

      student.setEmail(studentRequestDTO.email());
    }

    if(studentRequestDTO.proficiencyLevel() != null) {
      if(studentRequestDTO.proficiencyLevel().equals(student.getProficiencyLevel())) {
        throw new EqualProficiencyLevelException();
      }

      student.setProficiencyLevel(studentRequestDTO.proficiencyLevel());
    }

    student.setUpdatedAt(LocalDateTime.now());

    StudentEntity updatedStudentInformations = this.studentRepository.save(student);

    return StudentResponseDTO.fromEntity(updatedStudentInformations);
  }

}

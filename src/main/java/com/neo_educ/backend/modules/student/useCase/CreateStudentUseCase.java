package com.neo_educ.backend.modules.student.useCase;

import com.neo_educ.backend.exceptions.student.StudentAlreadyExistsException;
import com.neo_educ.backend.modules.student.dto.StudentRequestDTO;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStudentUseCase {

  @Autowired
  private StudentRepository studentRepository;

  public StudentResponseDTO execute(StudentRequestDTO StudentRequestDTO) {
    if(studentRepository.findByEmail(StudentRequestDTO.email()).isPresent()) {
      throw new StudentAlreadyExistsException();
    }

    StudentEntity entity = StudentEntity.builder()
        .name(StudentRequestDTO.name())
        .email(StudentRequestDTO.email())
        .proficiencyLevel(StudentRequestDTO.proficiencyLevel())
        .build();

    StudentEntity saved = studentRepository.save(entity);

    return StudentResponseDTO.fromEntity(saved);
  }

}

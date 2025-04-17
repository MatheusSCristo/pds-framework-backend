package com.neo_educ.backend.modules.student.useCase;

import com.neo_educ.backend.exceptions.student.StudentAlreadyExistsException;
import com.neo_educ.backend.modules.student.dto.CreateStudentRequestDTO;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStudentUseCase {

  @Autowired
  private StudentRepository studentRepository;

  public StudentResponseDTO execute(CreateStudentRequestDTO createStudentRequestDTO) {
    if(studentRepository.findByEmail(createStudentRequestDTO.email()).isPresent()) {
      throw new StudentAlreadyExistsException();
    }

    StudentEntity entity = StudentEntity.builder()
        .name(createStudentRequestDTO.name())
        .email(createStudentRequestDTO.email())
        .proficiencyLevel(createStudentRequestDTO.proficiencyLevel())
        .build();

    StudentEntity saved = studentRepository.save(entity);

    return StudentResponseDTO.fromEntity(saved);
  }

}

package com.neo_educ.backend.modules.student.useCase;

import com.neo_educ.backend.exceptions.student.StudentNotFoundException;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetStudentInformationsUseCase {

  @Autowired
  private StudentRepository studentRepository;

  public StudentResponseDTO execute(Long studentId) {
    StudentEntity entity = studentRepository.findById(studentId)
        .orElseThrow(StudentNotFoundException::new);

    return StudentResponseDTO.fromEntity(entity);
  }

}

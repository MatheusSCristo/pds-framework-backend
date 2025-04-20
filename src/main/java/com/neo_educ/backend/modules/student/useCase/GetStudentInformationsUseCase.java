package com.neo_educ.backend.modules.student.useCase;

import com.neo_educ.backend.exceptions.student.StudentNotFoundException;
import com.neo_educ.backend.modules.student.dto.StudentResponseDTO;
import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetStudentInformationsUseCase {

  @Autowired
  private StudentRepository studentRepository;

  public StudentResponseDTO execute(Long studentId, String teacherEmail) {
    StudentEntity entity = studentRepository.findByIdAndTeacherEmail(studentId, teacherEmail)
        .orElseThrow(StudentNotFoundException::new);

    return StudentResponseDTO.fromEntity(entity);
  }

}

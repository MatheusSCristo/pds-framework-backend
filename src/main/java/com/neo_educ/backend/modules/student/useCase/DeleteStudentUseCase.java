package com.neo_educ.backend.modules.student.useCase;

import com.neo_educ.backend.exceptions.student.StudentNotFoundException;
import com.neo_educ.backend.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class DeleteStudentUseCase {

  @Autowired
  private StudentRepository studentRepository;

  public void execute(Long studentId, String teacherEmail) {
    var student = studentRepository.findByIdAndTeacherEmail(studentId, teacherEmail)
        .orElseThrow(StudentNotFoundException::new);

    studentRepository.delete(student);
  }

}

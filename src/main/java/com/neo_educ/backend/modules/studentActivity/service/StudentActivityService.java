package com.neo_educ.backend.modules.studentActivity.service;

import com.neo_educ.backend.modules.student.entity.StudentEntity;
import com.neo_educ.backend.modules.student.service.StudentService;
import com.neo_educ.backend.modules.studentActivity.dto.StudentActivityCreateDTO;
import com.neo_educ.backend.modules.studentActivity.dto.StudentActivityResponseDTO;
import com.neo_educ.backend.modules.studentActivity.entity.StudentActivityEntity;
import com.neo_educ.backend.modules.studentActivity.mapper.StudentActivityMapper;
import com.neo_educ.backend.modules.studentActivity.repository.StudentActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentActivityService {

    @Autowired
    private StudentActivityRepository studentActivityRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentActivityMapper studentActivityMapper;

    public void createStudentActivity(StudentActivityCreateDTO studentActivityCreateDTO) {
        StudentEntity studentEntity=studentService.findStudent(studentActivityCreateDTO.studentId());
        StudentActivityEntity studentActivityEntity=studentActivityMapper.toEntity(studentActivityCreateDTO);
        studentActivityEntity.setStudent(studentEntity);
        studentActivityRepository.save(studentActivityEntity);
    }

    public List<StudentActivityResponseDTO> getStudentActivities(Long studentId) {
        StudentEntity studentEntity=studentService.findStudent(studentId);
        return studentEntity.getActivities().stream().map(studentActivityMapper::toDTO).toList();
    }


}

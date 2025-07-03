package com.neo_educ.backend.apps.english.studentActivity.service;

import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.apps.english.student.service.StudentService;
import com.neo_educ.backend.apps.english.studentActivity.dto.StudentActivityCreateDTO;
import com.neo_educ.backend.apps.english.studentActivity.dto.StudentActivityResponseDTO;
import com.neo_educ.backend.apps.english.studentActivity.entity.StudentActivityEntity;
import com.neo_educ.backend.apps.english.studentActivity.mapper.StudentActivityMapper;
import com.neo_educ.backend.apps.english.studentActivity.repository.StudentActivityRepository;

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

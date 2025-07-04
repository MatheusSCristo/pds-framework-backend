package com.neo_educ.backend.apps.english.studentActivity.service;

import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.apps.english.student.repository.StudentRepository;
import com.neo_educ.backend.apps.english.studentActivity.dto.StudentActivityCreateDTO;
import com.neo_educ.backend.apps.english.studentActivity.dto.StudentActivityResponseDTO;
import com.neo_educ.backend.apps.english.studentActivity.entity.StudentActivityEntity;
import com.neo_educ.backend.apps.english.studentActivity.mapper.StudentActivityMapper;
import com.neo_educ.backend.apps.english.studentActivity.repository.StudentActivityRepository;

import jakarta.persistence.EntityNotFoundException; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentActivityService {

    @Autowired
    private StudentActivityRepository studentActivityRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentActivityMapper studentActivityMapper;

    public void createStudentActivity(StudentActivityCreateDTO studentActivityCreateDTO) {
        StudentEntity studentEntity = studentRepository.findById(studentActivityCreateDTO.studentId())
                .orElseThrow(() -> new EntityNotFoundException());
        
        StudentActivityEntity studentActivityEntity = studentActivityMapper.toEntity(studentActivityCreateDTO);
        studentActivityEntity.setStudent(studentEntity);
        studentActivityRepository.save(studentActivityEntity);
    }

    public List<StudentActivityResponseDTO> getStudentActivities(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException());
        
        return studentEntity.getActivities().stream().map(studentActivityMapper::toDTO).toList();
    }
}
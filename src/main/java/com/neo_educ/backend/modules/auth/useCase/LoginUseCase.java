package com.neo_educ.backend.modules.auth.useCase;

import com.neo_educ.backend.modules.teacher.entity.TeacherEntity;
import com.neo_educ.backend.modules.auth.dto.LoginDTO;
import com.neo_educ.backend.modules.teacher.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherEntity execute(LoginDTO input) {
        return teacherRepository.findByEmail(input.email())
                .orElseThrow(()->new EntityNotFoundException("Usuário com email: "+input.email()+" não encontrado." ));
    }
}

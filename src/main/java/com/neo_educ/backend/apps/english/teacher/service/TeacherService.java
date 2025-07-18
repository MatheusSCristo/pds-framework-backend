package com.neo_educ.backend.apps.english.teacher.service;

// ... imports

import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.repository.TeacherRepository;
import com.neo_educ.backend.core.repository.AbstractRepository;
import com.neo_educ.backend.core.repository.AbstractUserRepository;
import com.neo_educ.backend.core.service.UserService;
import com.neo_educ.backend.exceptions.ApiException;
import com.neo_educ.backend.exceptions.teacher.TeacherAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("teacherService")
public class TeacherService implements UserService<TeacherEntity> {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public AbstractUserRepository<TeacherEntity> getRepository() {
        return teacherRepository;
    }

    @Override
    public ApiException getAlreadyExistsException() {
        return new TeacherAlreadyRegisteredException();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Professor não encontrado com o email: " + email));
    }

}
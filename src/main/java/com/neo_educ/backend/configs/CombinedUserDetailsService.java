package com.neo_educ.backend.configs;

import com.neo_educ.backend.apps.english.teacher.service.TeacherService;
import com.neo_educ.backend.apps.exercises.personal.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary // Mark this as the primary UserDetailsService bean
public class CombinedUserDetailsService implements UserDetailsService {

    private final TeacherService teacherService;
    private final PersonalService personalService;

    @Autowired
    public CombinedUserDetailsService(TeacherService teacherService, PersonalService personalService) {
        this.teacherService = teacherService;
        this.personalService = personalService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Try to load as a Teacher
            return teacherService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            // If not found as a Teacher, try to load as a Personal
            try {
                return personalService.loadUserByUsername(username);
            } catch (UsernameNotFoundException ex) {
                // If still not found, throw the original exception
                throw new UsernameNotFoundException("User not found with email: " + username);
            }
        }
    }
}
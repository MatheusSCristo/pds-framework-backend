package com.neo_educ.backend.core.service;


import com.neo_educ.backend.core.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserEntity signUp(UserEntity user);

}

package com.odilosigningapp.service;

import com.odilosigningapp.Models.User;
import com.odilosigningapp.dto.UserDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    User findAuthenticatedUser(Authentication authentication);

    List<UserDto> findAllUsers();
}

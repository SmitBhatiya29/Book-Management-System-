package com.springboot.BookManagementSystem.service;

import com.springboot.BookManagementSystem.dto.UserSignUpDto;
import com.springboot.BookManagementSystem.model.User;
import com.springboot.BookManagementSystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void addUser(UserSignUpDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());
        userRepository.save(user);
    }
}

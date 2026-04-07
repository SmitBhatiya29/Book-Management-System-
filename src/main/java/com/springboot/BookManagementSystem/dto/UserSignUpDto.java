package com.springboot.BookManagementSystem.dto;

import com.springboot.BookManagementSystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserSignUpDto(
        @NotBlank
        @NotNull
        @Size(min = 3, max = 15)
        String username,
        String password,
        Role role
) {
}

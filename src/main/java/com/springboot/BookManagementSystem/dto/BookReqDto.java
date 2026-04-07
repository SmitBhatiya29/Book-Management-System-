package com.springboot.BookManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Year;

public record BookReqDto(
        @NotNull
        @NotBlank
        String Title,
        @NotNull
        @NotBlank
        String Author,
        @NotNull
        Long ISBN,
        @NotNull
        Year publicationYear
) {
}

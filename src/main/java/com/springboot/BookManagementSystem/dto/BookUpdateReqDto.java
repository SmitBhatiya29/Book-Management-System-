package com.springboot.BookManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Year;

public record BookUpdateReqDto(

        String Title,
        String Author,
        Year publicationYear

) {
}

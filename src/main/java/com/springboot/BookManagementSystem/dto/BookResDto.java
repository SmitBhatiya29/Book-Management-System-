package com.springboot.BookManagementSystem.dto;

import java.time.Year;

public record BookResDto(
        String Title,
        String Author,
        long ISBN,
        Year publicationYear
) {
}

package com.springboot.BookManagementSystem.dto;

import java.time.Year;

public record BookReqDto(
        String Title,
        String Author,
        long ISBN,
        Year publicationYear
) {
}

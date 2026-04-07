package com.springboot.BookManagementSystem.dto;

import java.util.List;

public record BookPagResDto(
        List<BookResDto> data,
        long totalRecords,
        int totalPages
) {
}

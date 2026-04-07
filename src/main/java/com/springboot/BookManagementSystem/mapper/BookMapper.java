package com.springboot.BookManagementSystem.mapper;

import com.springboot.BookManagementSystem.dto.BookResDto;
import com.springboot.BookManagementSystem.model.Book;

public class BookMapper {
    public static BookResDto mapToDto(Book book) {
        return new BookResDto(
                book.getTitle(),
                book.getAuthor(),
                book.getISBN(),
                book.getPublicationYear()
        );
    }
}

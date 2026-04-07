package com.springboot.BookManagementSystem.repository;

import com.springboot.BookManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByISBN(long isbn);
}

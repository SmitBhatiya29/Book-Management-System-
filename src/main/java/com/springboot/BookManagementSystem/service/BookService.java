package com.springboot.BookManagementSystem.service;

import com.springboot.BookManagementSystem.dto.BookPagResDto;
import com.springboot.BookManagementSystem.dto.BookReqDto;
import com.springboot.BookManagementSystem.dto.BookResDto;
import com.springboot.BookManagementSystem.mapper.BookMapper;
import com.springboot.BookManagementSystem.model.Book;
import com.springboot.BookManagementSystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public void addBook(BookReqDto bookReqDto) {
        Book book = new Book();
        book.setTitle(bookReqDto.Title());
        book.setAuthor(bookReqDto.Author());
        book.setISBN(bookReqDto.ISBN());
        book.setPublicationYear(bookReqDto.publicationYear());
        bookRepository.save(book);

    }

    public BookPagResDto getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> pageBooks = bookRepository.findAll(pageable);
        long totalRecords =pageBooks.getTotalElements();
        int totalPages = pageBooks.getTotalPages();

        List<BookResDto> listDto = pageBooks
                .toList()
                .stream()
                .map(BookMapper:: mapToDto)
                .toList();
        return new BookPagResDto(
                listDto,
                totalRecords,
                totalPages
        );

    }
}

package com.springboot.BookManagementSystem.service;

import com.springboot.BookManagementSystem.dto.BookPagResDto;
import com.springboot.BookManagementSystem.dto.BookReqDto;
import com.springboot.BookManagementSystem.dto.BookResDto;
import com.springboot.BookManagementSystem.dto.BookUpdateReqDto;
import com.springboot.BookManagementSystem.exceptions.AlreadyExistsException;
import com.springboot.BookManagementSystem.exceptions.ResourceNotFoundException;
import com.springboot.BookManagementSystem.mapper.BookMapper;
import com.springboot.BookManagementSystem.model.Book;
import com.springboot.BookManagementSystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public void addBook(BookReqDto bookReqDto) {
        log.info("Adding a new book...");
        Book checkBook = bookRepository.findByISBN(bookReqDto.ISBN());
        if(checkBook != null){
            log.warn("Failed to add book...ISBN already exists.");
            throw  new AlreadyExistsException("Book is Already eists of this ISBN number");
        }

        Book book = new Book();
        book.setTitle(bookReqDto.Title());
        book.setAuthor(bookReqDto.Author());
        book.setISBN(bookReqDto.ISBN());
        book.setPublicationYear(bookReqDto.publicationYear());
        bookRepository.save(book);

        log.info("Successfully added book ");

    }

    public BookPagResDto getAllBooks(int page, int size) {
        log.info("Fetching all books");
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> pageBooks = bookRepository.findAll(pageable);
        long totalRecords =pageBooks.getTotalElements();
        int totalPages = pageBooks.getTotalPages();

        List<BookResDto> listDto = pageBooks
                .toList()
                .stream()
                .map(BookMapper:: mapToDto)
                .toList();
        log.info("Successfully fetched books.");
        return new BookPagResDto(
                listDto,
                totalRecords,
                totalPages
        );

    }

    public BookResDto getBookByISBN(long isbn) {
        log.info("Fetching book by ISBN");
        Book book = bookRepository.findByISBN(isbn);
        if(book == null){
            log.warn("Fetch failed. Book not found for Requesting ISBN");
            throw new ResourceNotFoundException("Book Not Found For This ISBN Number");
        }
        log.info("Successfully found book with ISBN");
        return  BookMapper.mapToDto(book);
    }

    public void updateBookByISBN(long isbn, BookUpdateReqDto bookUpdateReqDto) {
        log.info("Updating to book with ISBN Number");
        Book book = bookRepository.findByISBN(isbn);
        if(book == null){
            log.warn("Update failed. Book not found for This ISBN");
            throw new ResourceNotFoundException("Book Not Found For This ISBN Number So can not update.");
        }

        if (bookUpdateReqDto.Title() != null && !bookUpdateReqDto.Title().isEmpty()){
            book.setTitle(bookUpdateReqDto.Title());
        }
        if (bookUpdateReqDto.Author() != null && !bookUpdateReqDto.Author().isEmpty()){
            book.setAuthor(bookUpdateReqDto.Author());
        }
        if (bookUpdateReqDto.publicationYear() != null ){
            book.setPublicationYear(bookUpdateReqDto.publicationYear());
        }

        bookRepository.save(book);
        log.info("Successfully updated book ");
    }

    public void deleteBookByISBN(long isbn) {
        log.info("Deleteing the book ");
        Book book = bookRepository.findByISBN(isbn);
        if(book == null){
            log.warn("Deletion failed. Book not found for Requesting ISBN");
            throw new ResourceNotFoundException("Book Not Found For This ISBN Number So can not delete.");
        }
        bookRepository.delete(book);
        log.info("Successfully deleted book");
    }
}

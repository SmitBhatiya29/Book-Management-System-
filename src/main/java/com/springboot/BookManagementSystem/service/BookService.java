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

        Book checkBook = bookRepository.findByISBN(bookReqDto.ISBN());
        if(checkBook != null){
            throw  new AlreadyExistsException("Book is Already eists of this ISBN number");
        }

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

    public BookResDto getBookByISBN(long isbn) {
        Book book = bookRepository.findByISBN(isbn);
        if(book == null){
            throw new ResourceNotFoundException("Book Not Found For This ISBN Number");
        }
        return  BookMapper.mapToDto(book);
    }

    public void updateBookByISBN(long isbn, BookUpdateReqDto bookUpdateReqDto) {
        Book book = bookRepository.findByISBN(isbn);
        if(book == null){
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




    }
}

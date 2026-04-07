package com.springboot.BookManagementSystem.controller;

import com.springboot.BookManagementSystem.dto.BookPagResDto;
import com.springboot.BookManagementSystem.dto.BookReqDto;
import com.springboot.BookManagementSystem.dto.BookResDto;
import com.springboot.BookManagementSystem.dto.BookUpdateReqDto;
import com.springboot.BookManagementSystem.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookReqDto bookReqDto) {
        bookService.addBook(bookReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all-book")
    public BookPagResDto getAllBooks(@RequestParam(value = "page" , required = false,defaultValue = "0") int page,
                                     @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        return bookService.getAllBooks(page,size);
    }

    @GetMapping("/book-by-isbn")
    public BookResDto getBookByISBN(@RequestParam(value = "isbn" , required = true ) long isbn){
        return bookService.getBookByISBN(isbn);
    }

    @PutMapping("/update/{isbn}")
    public ResponseEntity<?> updateBookByISBN(
            @PathVariable long isbn,
            @RequestBody BookUpdateReqDto bookUpdateReqDto
    ){
        bookService.updateBookByISBN(isbn,bookUpdateReqDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

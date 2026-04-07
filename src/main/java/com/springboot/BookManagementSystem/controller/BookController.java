package com.springboot.BookManagementSystem.controller;

import com.springboot.BookManagementSystem.dto.BookPagResDto;
import com.springboot.BookManagementSystem.dto.BookReqDto;
import com.springboot.BookManagementSystem.service.BookService;
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
    public ResponseEntity<?> addBook(@RequestBody BookReqDto bookReqDto) {
        bookService.addBook(bookReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all-book")
    public BookPagResDto getAllBooks(@RequestParam(value = "page" , required = false,defaultValue = "0") int page,
                                     @RequestParam(value = "size",required = false,defaultValue = "5") int size){
        return bookService.getAllBooks(page,size);
    }

}

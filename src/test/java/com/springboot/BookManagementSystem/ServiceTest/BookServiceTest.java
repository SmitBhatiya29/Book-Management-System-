package com.springboot.BookManagementSystem.ServiceTest;

import com.springboot.BookManagementSystem.dto.BookPagResDto;
import com.springboot.BookManagementSystem.dto.BookResDto;
import com.springboot.BookManagementSystem.exceptions.ResourceNotFoundException;
import com.springboot.BookManagementSystem.model.Book;
import com.springboot.BookManagementSystem.repository.BookRepository;
import com.springboot.BookManagementSystem.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Year;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void getBookByISBNTest() {
        Assertions.assertNotNull(bookService);

        Long ISBN = 9781234567890L;
        Book book = new Book();
        book.setTitle("Spring Boot");
        book.setAuthor("SomeOne");
        book.setISBN(ISBN);
        book.setPublicationYear(Year.now());
        //exception testing
        Long invalidIsbn = 1111111111111L;

        Mockito.when(bookRepository.findByISBN(ISBN)).thenReturn(book);
        //exception
        Mockito.when(bookRepository.findByISBN(invalidIsbn)).thenReturn(null);

        BookResDto dto = new BookResDto(
                book.getTitle(),
                book.getAuthor(),
                book.getISBN(),
                book.getPublicationYear()
        );

        BookResDto wrongDto = new BookResDto(
                "Title",
                "Author",
                ISBN,
                Year.of(2023)
        );
        //exception
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookByISBN(invalidIsbn);
        });

        Assertions.assertEquals(dto, bookService.getBookByISBN(ISBN));
        Assertions.assertNotEquals(wrongDto, bookService.getBookByISBN(ISBN));
        //exception
        Assertions.assertEquals("Book Not Found For This ISBN Number", e.getMessage());

        Mockito.verify(bookRepository, Mockito.times(2)).findByISBN(ISBN);
        //exception
        Mockito.verify(bookRepository, Mockito.times(1)).findByISBN(invalidIsbn);
    }

    @Test
    public void getAllBooksTest() {
        Book book1 = new Book();
        book1.setTitle("Somthing");
        book1.setAuthor("Nothing");
        book1.setISBN(123456L);
        book1.setPublicationYear(Year.now());

        Book book2 = new Book();
        book2.setTitle("C");
        book2.setAuthor("Smit");
        book2.setISBN(654321L);
        book2.setPublicationYear(Year.now());

        List<Book> listBook = List.of(book1, book2);

        Page<Book> pageBook = new PageImpl<>(listBook);
        int page = 0;
        int size = 2;

        Pageable pageable = PageRequest.of(page, size);

        Mockito.when(bookRepository.findAll(pageable)).thenReturn(pageBook);


        BookPagResDto result = bookService.getAllBooks(page, size);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.totalRecords()); // Based on new BookPagResDto(listDto, totalRecords, totalPages)
        Assertions.assertEquals(1, result.totalPages());

        Mockito.verify(bookRepository, Mockito.times(1)).findAll(pageable);
    }

}

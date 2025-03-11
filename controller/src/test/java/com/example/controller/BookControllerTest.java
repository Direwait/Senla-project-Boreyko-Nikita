package com.example.controller;

import com.example.dto.BookDTO;
import com.example.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        BookDTO book1 = new BookDTO();
        book1.setBookTitle("Book 1");
        BookDTO book2 = new BookDTO();
        book2.setBookTitle("Book 2");
        List<BookDTO> books = Arrays.asList(book1, book2);

        when(bookService.getAll()).thenReturn(books);

        List<BookDTO> result = bookController.getAll();

        assertEquals(2, result.size());
        verify(bookService).getAll();
    }

    @Test
    void getByIdTest() {
        BookDTO book = new BookDTO();
        book.setBookTitle("Book 1");
        when(bookService.getById(1)).thenReturn(book);

        ResponseEntity<BookDTO> response = bookController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService).getById(1);
    }

    @Test
    void createBookTest() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookTitle("New Book");
        when(bookService.createBook(any(BookDTO.class))).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.createBook(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(bookService).createBook(bookDTO);
    }

    @Test
    void updateBookTest() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookTitle("Updated Book");
        when(bookService.updateBook(1, bookDTO)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.updateBook(1, bookDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(bookService).updateBook(1, bookDTO);
    }

    @Test
    void deleteBookTest() {
        ResponseEntity<Void> response = bookController.deleteBook(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService).deleteById(1);
    }
}

package com.example.controller;

import com.example.dto.BookCatalogDTO;
import com.example.service.BookCatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class BookCatalogControllerTest {

    @InjectMocks
    private BookCatalogController bookCatalogController;

    @Mock
    private BookCatalogService bookCatalogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        BookCatalogDTO dto = new BookCatalogDTO();
        List<BookCatalogDTO> bookCatalogDTOS = List.of(dto);

        when(bookCatalogService.getAll()).thenReturn(bookCatalogDTOS);

        ResponseEntity<List<BookCatalogDTO>> response = bookCatalogController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookCatalogService).getAll();
    }

    @Test
    void getByIdTest() {
        BookCatalogDTO bookCatalogDTO = new BookCatalogDTO();
        when(bookCatalogService.getById(1)).thenReturn(bookCatalogDTO);

        ResponseEntity<BookCatalogDTO> response = bookCatalogController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookCatalogDTO, response.getBody());
        verify(bookCatalogService).getById(1);
    }

    @Test
    void testCreateBookCatalog() {
        BookCatalogDTO bookCatalogDTO = new BookCatalogDTO();
        when(bookCatalogService.createBookCatalog(any(BookCatalogDTO.class))).thenReturn(bookCatalogDTO);

        ResponseEntity<BookCatalogDTO> response = bookCatalogController.createBookCatalog(bookCatalogDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookCatalogDTO, response.getBody());
        verify(bookCatalogService).createBookCatalog(bookCatalogDTO);
    }

    @Test
    void testUpdateBookCatalog() {
        BookCatalogDTO bookCatalogDTO = new BookCatalogDTO();
        when(bookCatalogService.updateBookCatalog(1, bookCatalogDTO)).thenReturn(bookCatalogDTO);

        ResponseEntity<BookCatalogDTO> response = bookCatalogController.updateBookCatalog(1, bookCatalogDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookCatalogDTO, response.getBody());
        verify(bookCatalogService).updateBookCatalog(1, bookCatalogDTO);
    }

    @Test
    void testDeleteBookCatalog() {
        ResponseEntity<Void> response = bookCatalogController.deleteBookCatalog(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookCatalogService).deleteById(1);
    }
}

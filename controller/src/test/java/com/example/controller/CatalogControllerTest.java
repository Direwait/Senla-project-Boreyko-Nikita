package com.example.controller;

import com.example.dto.CatalogDTO;
import com.example.service.CatalogService;
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

class CatalogControllerTest {
    @InjectMocks
    private CatalogController catalogController;

    @Mock
    private CatalogService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        CatalogDTO book1 = new CatalogDTO();
        CatalogDTO book2 = new CatalogDTO();
        List<CatalogDTO> books = Arrays.asList(book1, book2);

        when(bookService.getAll()).thenReturn(books);

        List<CatalogDTO> result = catalogController.getAll();

        assertEquals(2, result.size());
        verify(bookService).getAll();
    }

    @Test
    void getByIdTest() {
        CatalogDTO book = new CatalogDTO();
        when(bookService.getById(1)).thenReturn(book);

        ResponseEntity<CatalogDTO> response = catalogController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService).getById(1);
    }

    @Test
    void createCatalogTest() {
        CatalogDTO bookDTO = new CatalogDTO();
        when(bookService.createCatalog(any(CatalogDTO.class))).thenReturn(bookDTO);

        ResponseEntity<CatalogDTO> response = catalogController.createCatalog(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(bookService).createCatalog(bookDTO);
    }

    @Test
    void updateBookTest() {
        CatalogDTO catalogDTO = new CatalogDTO();
        when(bookService.updateCatalog(1, catalogDTO)).thenReturn(catalogDTO);

        ResponseEntity<CatalogDTO> response = catalogController.updateCatalog(1, catalogDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(catalogDTO, response.getBody());
        verify(bookService).updateCatalog(1, catalogDTO);
    }

    @Test
    void deleteBookTest() {
        ResponseEntity<Void> response = catalogController.deleteCatalog(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService).deleteById(1);
    }
}

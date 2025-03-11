package com.example.controller;


import com.example.dto.RentalDTO;
import com.example.service.RentalService;
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

class RentalControllerTest {
    @InjectMocks
    private RentalController catalogController;

    @Mock
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        RentalDTO book1 = new RentalDTO();
        RentalDTO book2 = new RentalDTO();
        List<RentalDTO> books = Arrays.asList(book1, book2);

        when(rentalService.getAll()).thenReturn(books);

        List<RentalDTO> result = catalogController.getAll();

        assertEquals(2, result.size());
        verify(rentalService).getAll();
    }

    @Test
    void getByIdTest() {
        RentalDTO book = new RentalDTO();
        when(rentalService.getById(1)).thenReturn(book);

        ResponseEntity<RentalDTO> response = catalogController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(rentalService).getById(1);
    }

    @Test
    void createBookTest() {
        RentalDTO bookDTO = new RentalDTO();
        when(rentalService.createRental(any(RentalDTO.class))).thenReturn(bookDTO);

        ResponseEntity<RentalDTO> response = catalogController.createBook(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(rentalService).createRental(bookDTO);
    }

    @Test
    void updateBookTest() {
        RentalDTO catalogDTO = new RentalDTO();
        when(rentalService.updateRental(1, catalogDTO)).thenReturn(catalogDTO);

        ResponseEntity<RentalDTO> response = catalogController.updateCatalog(1, catalogDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(catalogDTO, response.getBody());
        verify(rentalService).updateRental(1, catalogDTO);
    }

    @Test
    void deleteBookTest() {
        ResponseEntity<Void> response = catalogController.deleteCatalog(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(rentalService).deleteById(1);
    }
}
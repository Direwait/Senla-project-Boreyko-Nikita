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
    private RentalController rentalController;

    @Mock
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        RentalDTO rentalDTO = new RentalDTO();
        RentalDTO rentalDTO1 = new RentalDTO();
        List<RentalDTO> books = Arrays.asList(rentalDTO, rentalDTO1);

        when(rentalService.getAll()).thenReturn(books);

        ResponseEntity<List<RentalDTO>> response = rentalController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(rentalService).getAll();
    }

    @Test
    void getByIdTest() {
        RentalDTO rentalDTO = new RentalDTO();
        when(rentalService.getById(1)).thenReturn(rentalDTO);

        ResponseEntity<RentalDTO> response = rentalController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rentalDTO, response.getBody());
        verify(rentalService).getById(1);
    }

    @Test
    void createRentalTest() {
        RentalDTO rentalDTO = new RentalDTO();
        when(rentalService.createRental(any(RentalDTO.class))).thenReturn(rentalDTO);

        ResponseEntity<RentalDTO> response = rentalController.createRental(rentalDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(rentalDTO, response.getBody());
        verify(rentalService).createRental(rentalDTO);
    }

    @Test
    void updateRentalTest() {
        RentalDTO rentalDTO = new RentalDTO();
        when(rentalService.updateRental(1, rentalDTO)).thenReturn(rentalDTO);

        ResponseEntity<RentalDTO> response = rentalController.updateRental(1, rentalDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rentalDTO, response.getBody());
        verify(rentalService).updateRental(1, rentalDTO);
    }

    @Test
    void deleteRentalTest() {
        ResponseEntity<Void> response = rentalController.deleteRental(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(rentalService).deleteById(1);
    }

    @Test
    void getActiveRentalsTest() {
        RentalDTO book1 = new RentalDTO();
        RentalDTO book2 = new RentalDTO();
        List<RentalDTO> books = Arrays.asList(book1, book2);

        when(rentalService.getActiveRentals()).thenReturn(books);

        ResponseEntity<List<RentalDTO>> response = rentalController.getActiveRentals();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(rentalService).getActiveRentals();
    }

    @Test
    void getReturnedRentalsTest() {
        RentalDTO rental1 = new RentalDTO();
        RentalDTO rental2 = new RentalDTO();
        when(rentalService.getReturnedRentals()).thenReturn(List.of(rental1, rental2));

        ResponseEntity<List<RentalDTO>> response = rentalController.getReturnedRentals();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(rentalService).getReturnedRentals();
    }

    @Test
    void getOverdueRentalsTest() {
        RentalDTO rental1 = new RentalDTO();
        RentalDTO rental2 = new RentalDTO();
        when(rentalService.getOverdueRentals()).thenReturn(List.of(rental1, rental2));

        ResponseEntity<List<RentalDTO>> response = rentalController.getOverdueRentals();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(rentalService).getOverdueRentals();
    }

    @Test
    void returnBookTest() {
        Integer rentalId = 1;
        RentalDTO expectedDTO = new RentalDTO();
        expectedDTO.setRentalId(rentalId);

        when(rentalService.returnBook(rentalId)).thenReturn(expectedDTO);

        ResponseEntity<RentalDTO> response = rentalController.returnBook(rentalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());

        verify(rentalService).returnBook(rentalId);
    }
}

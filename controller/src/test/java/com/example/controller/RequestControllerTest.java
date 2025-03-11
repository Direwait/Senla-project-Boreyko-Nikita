package com.example.controller;


import com.example.dto.RequestDTO;
import com.example.service.RequestService;
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

class RequestControllerTest {
    @InjectMocks
    private RequestController catalogController;

    @Mock
    private RequestService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        RequestDTO book1 = new RequestDTO();
        RequestDTO book2 = new RequestDTO();
        List<RequestDTO> books = Arrays.asList(book1, book2);

        when(rentalService.getAll()).thenReturn(books);

        List<RequestDTO> result = catalogController.getAll();

        assertEquals(2, result.size());
        verify(rentalService).getAll();
    }

    @Test
    void getByIdTest() {
        RequestDTO book = new RequestDTO();
        when(rentalService.getById(1)).thenReturn(book);

        ResponseEntity<RequestDTO> response = catalogController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(rentalService).getById(1);
    }

    @Test
    void createBookTest() {
        RequestDTO bookDTO = new RequestDTO();
        when(rentalService.createRequest(any(RequestDTO.class))).thenReturn(bookDTO);

        ResponseEntity<RequestDTO> response = catalogController.createRequest(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(rentalService).createRequest(bookDTO);
    }

    @Test
    void updateBookTest() {
        RequestDTO requestDTO = new RequestDTO();
        when(rentalService.updateRequest(1, requestDTO)).thenReturn(requestDTO);

        ResponseEntity<RequestDTO> response = catalogController.updateRequest(1, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requestDTO, response.getBody());
        verify(rentalService).updateRequest(1, requestDTO);
    }

    @Test
    void deleteBookTest() {
        ResponseEntity<Void> response = catalogController.deleteRequest(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(rentalService).deleteById(1);
    }
}
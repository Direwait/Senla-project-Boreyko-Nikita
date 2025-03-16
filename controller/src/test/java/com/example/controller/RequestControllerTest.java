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
    private RequestController requestController;

    @Mock
    private RequestService requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        RequestDTO book1 = new RequestDTO();
        RequestDTO book2 = new RequestDTO();
        List<RequestDTO> books = Arrays.asList(book1, book2);

        when(requestService.getAll()).thenReturn(books);

        ResponseEntity<List<RequestDTO>> response = requestController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(requestService).getAll();
    }

    @Test
    void testGetById() {
        RequestDTO book = new RequestDTO();
        when(requestService.getById(1)).thenReturn(book);

        ResponseEntity<RequestDTO> response = requestController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(requestService).getById(1);
    }

    @Test
    void testCreateRequest() {
        RequestDTO requestDTO = new RequestDTO();
        when(requestService.createRequest(any(RequestDTO.class))).thenReturn(requestDTO);

        ResponseEntity<RequestDTO> response = requestController.createRequest(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(requestDTO, response.getBody());
        verify(requestService).createRequest(requestDTO);
    }


    @Test
    void testUpdateRequest() {
        RequestDTO requestDTO = new RequestDTO();
        when(requestService.updateRequest(1, requestDTO)).thenReturn(requestDTO);

        ResponseEntity<RequestDTO> response = requestController.updateRequest(1, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requestDTO, response.getBody());
        verify(requestService).updateRequest(1, requestDTO);
    }

    @Test
    void testDeleteRequest() {
        ResponseEntity<Void> response = requestController.deleteRequest(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(requestService).deleteById(1);
    }
}
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
    private CatalogService catalogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTest() {
        CatalogDTO catalogDTO = new CatalogDTO();
        CatalogDTO catalogDTO1 = new CatalogDTO();
        List<CatalogDTO> catalogDTOS = Arrays.asList(catalogDTO, catalogDTO1);

        when(catalogService.getAll()).thenReturn(catalogDTOS);

        ResponseEntity<List<CatalogDTO>> response = catalogController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(catalogService).getAll();
    }

    @Test
    void getByIdTest() {
        CatalogDTO catalogDTO = new CatalogDTO();
        when(catalogService.getById(1)).thenReturn(catalogDTO);

        ResponseEntity<CatalogDTO> response = catalogController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(catalogDTO, response.getBody());
        verify(catalogService).getById(1);
    }

    @Test
    void createCatalogTest() {
        CatalogDTO catalogDTO = new CatalogDTO();
        when(catalogService.createCatalog(any(CatalogDTO.class))).thenReturn(catalogDTO);

        ResponseEntity<CatalogDTO> response = catalogController.createCatalog(catalogDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(catalogDTO, response.getBody());
        verify(catalogService).createCatalog(catalogDTO);
    }

    @Test
    void updateCatalogTest() {
        CatalogDTO catalogDTO = new CatalogDTO();
        when(catalogService.updateCatalog(1, catalogDTO)).thenReturn(catalogDTO);

        ResponseEntity<CatalogDTO> response = catalogController.updateCatalog(1, catalogDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(catalogDTO, response.getBody());
        verify(catalogService).updateCatalog(1, catalogDTO);
    }

    @Test
    void deleteCatalogTest() {
        ResponseEntity<Void> response = catalogController.deleteCatalog(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(catalogService).deleteById(1);
    }

    @Test
    void getRootsTest() {
        CatalogDTO catalogDTO = new CatalogDTO();
        CatalogDTO catalogDTO1 = new CatalogDTO();
        List<CatalogDTO> catalogDTOS = Arrays.asList(catalogDTO, catalogDTO1);

        when(catalogService.getAll()).thenReturn(catalogDTOS);

        ResponseEntity<List<CatalogDTO>> response = catalogController.getRoots();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(catalogService).getAllRootCatalogs();
    }
}

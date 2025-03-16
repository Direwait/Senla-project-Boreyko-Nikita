package com.example.service;

import com.example.dto.CatalogDTO;
import com.example.mapper.CatalogMapper;
import com.example.model.Catalog;
import com.example.repository.CatalogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {

    @InjectMocks
    CatalogService catalogService;
    @Mock
    CatalogRepository catalogRepository;
    @Mock
    CatalogMapper catalogMapper;

    @Test
    void testGetById() {
        Integer id = 1;
        Catalog catalog = new Catalog();
        CatalogDTO catalogDTO = new CatalogDTO();

        when(catalogRepository.findById(id)).thenReturn(Optional.of(catalog));
        when(catalogMapper.modelToDTO(catalog)).thenReturn(catalogDTO);

        CatalogDTO result = catalogService.getById(id);

        assertNotNull(result);
        assertEquals(catalogDTO, result);
        verify(catalogRepository).findById(id);
    }


    @Test
    void testGetAll() {
        Catalog catalog1 = new Catalog();
        Catalog catalog2 = new Catalog();
        List<Catalog> catalogs = List.of(catalog1, catalog2);
        List<CatalogDTO> catalogDTOs = List.of(new CatalogDTO(), new CatalogDTO()); // Список DTO

        when(catalogRepository.findAll()).thenReturn(catalogs);
        when(catalogMapper.modelToDTO(catalog1)).thenReturn(catalogDTOs.get(0));
        when(catalogMapper.modelToDTO(catalog2)).thenReturn(catalogDTOs.get(1));

        List<CatalogDTO> result = catalogService.getAll();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(catalogDTOs));
        verify(catalogRepository).findAll();
    }

    @Test
    void testDeleteById() {
        Integer requestId = 1;
        when(catalogRepository.existsById(requestId)).thenReturn(true);

        catalogService.deleteById(requestId);

        verify(catalogRepository).existsById(requestId);
        verify(catalogRepository).deleteById(requestId);
    }

    @Test
    void testCreateCatalog() {
        Catalog catalog = new Catalog();
        CatalogDTO catalogDTO = new CatalogDTO();
        Catalog savedCatalog = new Catalog();

        when(catalogMapper.dtoToModel(catalogDTO)).thenReturn(catalog);
        when(catalogRepository.save(catalog)).thenReturn(savedCatalog);
        when(catalogMapper.modelToDTO(savedCatalog)).thenReturn(catalogDTO);

        CatalogDTO result = catalogService.createCatalog(catalogDTO);

        assertEquals(catalogDTO, result);
        verify(catalogRepository).save(catalog);
    }

    @Test
    void testUpdateCatalog() {
        var catalog = new Catalog();
        catalog.setCatalogId(1);
        catalog.setCatalogTitle("Old Title");

        var catalogDTO = new CatalogDTO();
        catalogDTO.setCatalogTitle("New Title");

        var updatedCatalog = new Catalog();
        updatedCatalog.setCatalogTitle("New Title");

        when(catalogRepository.findById(1)).thenReturn(Optional.of(catalog));
        when(catalogMapper.modelToDTO(Mockito.any(Catalog.class))).thenReturn(catalogDTO);
        when(catalogRepository.save(Mockito.any(Catalog.class))).thenReturn(updatedCatalog);

        CatalogDTO updatedCatalogDTO = catalogService.updateCatalog(1, catalogDTO);

        assertNotNull(updatedCatalogDTO);
        assertEquals("New Title", updatedCatalogDTO.getCatalogTitle());

        verify(catalogRepository).findById(1);
        verify(catalogMapper).updateCatalogFromDTO(catalogDTO, catalog);
        verify(catalogRepository).save(catalog);
        verifyNoMoreInteractions(catalogRepository, catalogMapper);
    }
}

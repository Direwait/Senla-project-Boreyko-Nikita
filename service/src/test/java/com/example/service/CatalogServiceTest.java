package com.example.service;

import com.example.dto.CatalogDTO;
import com.example.mapper.CatalogMapper;
import com.example.model.Catalog;
import com.example.repository.CatalogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {

    @InjectMocks
    CatalogService catalogService;
    @Mock
    CatalogRepository catalogRepository;
    @Mock
    CatalogMapper catalogMapper;

    @Test
    void testGetByIdTest() {
        Integer id = 1;
        Catalog catalog = new Catalog();
        CatalogDTO catalogDTO = new CatalogDTO();

        Mockito.when(catalogRepository.findById(id)).thenReturn(Optional.of(catalog));
        Mockito.when(catalogMapper.modelToDTO(catalog)).thenReturn(catalogDTO);

        CatalogDTO result = catalogService.getById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(catalogDTO, result);
        Mockito.verify(catalogRepository).findById(id);
    }


    @Test
    void testGetAllTest() {
        Catalog catalog1 = new Catalog();
        Catalog catalog2 = new Catalog();
        List<Catalog> catalogs = List.of(catalog1, catalog2);
        List<CatalogDTO> catalogDTOs = List.of(new CatalogDTO(), new CatalogDTO()); // Список DTO

        Mockito.when(catalogRepository.findAll()).thenReturn(catalogs);
        Mockito.when(catalogMapper.modelToDTO(catalog1)).thenReturn(catalogDTOs.get(0));
        Mockito.when(catalogMapper.modelToDTO(catalog2)).thenReturn(catalogDTOs.get(1));

        List<CatalogDTO> result = catalogService.getAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsAll(catalogDTOs));
        Mockito.verify(catalogRepository).findAll();
    }

    @Test
    void DeleteByIdTest() {
        Integer id = 1;

        catalogService.deleteById(id);

        Mockito.verify(catalogRepository).deleteById(id);
    }

    @Test
    void CreateDtoTest() {
        Catalog catalog = new Catalog();
        CatalogDTO catalogDTO = new CatalogDTO();
        Catalog savedCatalog = new Catalog();

        Mockito.when(catalogMapper.dtoToModel(catalogDTO)).thenReturn(catalog);
        Mockito.when(catalogRepository.save(catalog)).thenReturn(savedCatalog);
        Mockito.when(catalogMapper.modelToDTO(savedCatalog)).thenReturn(catalogDTO);

        CatalogDTO result = catalogService.createCatalog(catalogDTO);

        Assertions.assertEquals(catalogDTO, result);
        Mockito.verify(catalogRepository).save(catalog);
    }

    @Test
    void UpdateDtoTest() {
        var catalog = new Catalog();
        catalog.setCatalogId(1);
        catalog.setCatalogTitle("Old Title");

        var catalogDTO = new CatalogDTO();
        catalogDTO.setCatalogTitle("New Title");

        var updatedCatalog = new Catalog();
        updatedCatalog.setCatalogTitle("New Title");

        Mockito.when(catalogRepository.findById(1)).thenReturn(Optional.of(catalog));
        Mockito.when(catalogMapper.modelToDTO(Mockito.any(Catalog.class))).thenReturn(catalogDTO);
        Mockito.when(catalogRepository.save(Mockito.any(Catalog.class))).thenReturn(updatedCatalog);

        CatalogDTO updatedCatalogDTO = catalogService.updateCatalog(1, catalogDTO);

        Assertions.assertNotNull(updatedCatalogDTO);
        Assertions.assertEquals("New Title", updatedCatalogDTO.getCatalogTitle());

        Mockito.verify(catalogRepository).findById(1);
        Mockito.verify(catalogMapper).updateCatalogFromDTO(catalogDTO, catalog);
        Mockito.verify(catalogRepository).save(catalog);
        Mockito.verifyNoMoreInteractions(catalogRepository, catalogMapper);
    }
}

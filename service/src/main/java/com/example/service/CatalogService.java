package com.example.service;

import com.example.dto.CatalogDTO;
import com.example.mapper.CatalogMapper;
import com.example.model.Catalog;
import com.example.repository.CatalogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    @Transactional
    public CatalogDTO getById(Integer id) {
        Catalog bookCatalog = catalogRepository.findById(id).orElseThrow();
        return catalogMapper.modelToDTO(bookCatalog);
    }

    public List<CatalogDTO> getAll() {
        return catalogRepository.findAll().stream()
                .map(catalogMapper::modelToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        catalogRepository.deleteById(id);
    }

    @Transactional
    public CatalogDTO createCatalog(CatalogDTO catalogDTO) {
        Catalog catalog = catalogMapper.dtoToModel(catalogDTO);
        Catalog save = catalogRepository.save(catalog);
        return catalogMapper.modelToDTO(save);
    }

    @Transactional
    public CatalogDTO updateCatalog(Integer id, CatalogDTO catalogDTO) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Каталог с ID " + id + " не найдена"));
        catalogMapper.updateCatalogFromDTO(catalogDTO, catalog);
        Catalog updatedBook = catalogRepository.save(catalog);

        return catalogMapper.modelToDTO(updatedBook);
    }
}
package com.example.service;

import com.example.dto.CatalogDTO;
import com.example.mapper.CatalogMapper;
import com.example.model.Catalog;
import com.example.repository.CatalogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    public List<CatalogDTO> getAllRootCatalogs() {
        List<CatalogDTO> catalogDTOS = catalogRepository
                .findAllCatalogParent()
                .stream()
                .map(catalogMapper::modelToDTO)
                .toList();

        log.info("Found {} catalogs", catalogDTOS.size());
        return catalogDTOS;
    }


    @Transactional
    public CatalogDTO getById(Integer id) {
        Catalog bookCatalog = catalogRepository.findById(id)
                .orElseThrow(
                        () -> {
                            log.error("Catalog with ID {} not found ", id);
                            return new EntityNotFoundException("Catalog with ID " + id + " not found");
                        });

        log.info("Successfully retrieved Catalog ID: {}", id);
        return catalogMapper.modelToDTO(bookCatalog);
    }

    public List<CatalogDTO> getAll() {
        List<CatalogDTO> catalogDTOS = catalogRepository.findAll().stream()
                .map(catalogMapper::modelToDTO)
                .collect(Collectors.toList());

        log.info("Found {} catalogs ", catalogDTOS.size());
        return catalogDTOS;
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!catalogRepository.existsById(id)) {
            log.error("Delete failed: Catalog with ID {} not found", id);
            throw new EntityNotFoundException("Catalog with ID " + id + " not found");
        }

        catalogRepository.deleteById(id);
        log.info("Successfully deleted Catalog with ID: {}", id);
    }

    @Transactional
    public CatalogDTO createCatalog(CatalogDTO catalogDTO) {
        Catalog newCatalog = catalogMapper.dtoToModel(catalogDTO);

        if (catalogDTO.getParentCatalogId() != null) {
            Catalog parent = catalogRepository.findById(catalogDTO.getParentCatalogId())
                    .orElseThrow(
                            () -> {
                                log.error("Parent catalog not found");
                                return new EntityNotFoundException("Parent catalog not found");
                            });
            newCatalog.setCatalogParent(parent);
        }
        Catalog savedCatalog = catalogRepository.save(newCatalog);

        if (savedCatalog.getCatalogParent() != null) {
            Catalog parent = savedCatalog.getCatalogParent();
            parent.getSubCatalogs().add(savedCatalog);
            catalogRepository.save(parent);
        }

        log.info("Successfully created Catalog ID: {} - {}", savedCatalog.getCatalogId(), savedCatalog.getCatalogTitle());
        return catalogMapper.modelToDTO(savedCatalog);
    }

    @Transactional
    public CatalogDTO updateCatalog(Integer id, CatalogDTO catalogDTO) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(
                        () -> {
                            log.error("Catalog with ID {} not found", id);
                            return new EntityNotFoundException("Catalog with ID " + id + " not found");
                        });
        catalogMapper.updateCatalogFromDTO(catalogDTO, catalog);
        Catalog updatedCatalog = catalogRepository.save(catalog);

        log.info("Successfully updated Book ID: {} - {}", id, updatedCatalog.getCatalogTitle());
        return catalogMapper.modelToDTO(updatedCatalog);
    }
}
package com.example.controller;


import com.example.dto.CatalogDTO;
import com.example.service.CatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/catalogs")
@RestController
@AllArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<List<CatalogDTO>> getAll() {
        return ResponseEntity.ok(catalogService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogDTO> getById(@PathVariable Integer id) {
        CatalogDTO byId = catalogService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<CatalogDTO> createCatalog(@Valid @RequestBody CatalogDTO CatalogDTO) {
        CatalogDTO catalog = catalogService.createCatalog(CatalogDTO);
        return new ResponseEntity<>(catalog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogDTO> updateCatalog(
            @PathVariable Integer id,
            @Valid @RequestBody CatalogDTO CatalogDTO
    ) {
        CatalogDTO updateBookCatalog = catalogService.updateCatalog(id, CatalogDTO);
        return ResponseEntity.ok(updateBookCatalog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatalog(@PathVariable Integer id) {
        catalogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/roots")
    public ResponseEntity<List<CatalogDTO>> getRoots() {
        return ResponseEntity.ok(catalogService.getAllRootCatalogs());
    }
}

package com.example.controller;

import com.example.dto.BookCatalogDTO;
import com.example.service.BookCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bookCatalogs")
@RestController
@AllArgsConstructor
public class BookCatalogController {
    private final BookCatalogService bookCatalogService;

    @GetMapping
    public ResponseEntity<List<BookCatalogDTO>> getAll() {
        return ResponseEntity.ok(bookCatalogService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCatalogDTO> getById(@PathVariable Integer id) {
        BookCatalogDTO byId = bookCatalogService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<BookCatalogDTO> createBookCatalog(@Valid @RequestBody BookCatalogDTO bookCatalogDTO) {
        BookCatalogDTO bookCatalog = bookCatalogService.createBookCatalog(bookCatalogDTO);
        return new ResponseEntity<>(bookCatalog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCatalogDTO> updateBookCatalog(
            @PathVariable Integer id,
            @Valid @RequestBody BookCatalogDTO bookCatalogDTO
    ) {
        BookCatalogDTO updatedBook = bookCatalogService.updateBookCatalog(id, bookCatalogDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCatalog(@PathVariable Integer id) {
        bookCatalogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

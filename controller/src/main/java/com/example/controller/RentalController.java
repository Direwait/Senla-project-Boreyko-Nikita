package com.example.controller;


import com.example.dto.RentalDTO;
import com.example.service.RentalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rentals")
@RestController
@AllArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public List<RentalDTO> getAll() {
        return rentalService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getById(@PathVariable Integer id) {
        RentalDTO byId = rentalService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<RentalDTO> createBook(@Valid @RequestBody RentalDTO RentalDTO) {
        RentalDTO catalog = rentalService.createRental(RentalDTO);
        return new ResponseEntity<>(catalog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateCatalog(
            @PathVariable Integer id,
            @Valid @RequestBody RentalDTO RentalDTO
    ) {
        RentalDTO updateBookCatalog = rentalService.updateRental(id, RentalDTO);
        return ResponseEntity.ok(updateBookCatalog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatalog(@PathVariable Integer id) {
        rentalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

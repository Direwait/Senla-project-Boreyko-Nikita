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
    public ResponseEntity<List<RentalDTO>> getAll() {
        return ResponseEntity.ok(rentalService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getById(@PathVariable Integer id) {
        RentalDTO byId = rentalService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<RentalDTO> createRental(@Valid @RequestBody RentalDTO RentalDTO) {
        RentalDTO catalog = rentalService.createRental(RentalDTO);
        return new ResponseEntity<>(catalog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(
            @PathVariable Integer id,
            @Valid @RequestBody RentalDTO RentalDTO
    ) {
        RentalDTO updateBookCatalog = rentalService.updateRental(id, RentalDTO);
        return ResponseEntity.ok(updateBookCatalog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<RentalDTO> returnBook(@PathVariable Integer id) {
        RentalDTO dto = rentalService.returnBook(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<RentalDTO>> getOverdueRentals() {
        List<RentalDTO> rentals = rentalService.getOverdueRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/active")
    public ResponseEntity<List<RentalDTO>> getActiveRentals() {
        List<RentalDTO> rentals = rentalService.getActiveRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/returned")
    public ResponseEntity<List<RentalDTO>> getReturnedRentals() {
        List<RentalDTO> rentals = rentalService.getReturnedRentals();
        return ResponseEntity.ok(rentals);
    }
}

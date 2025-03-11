package com.example.controller;


import com.example.dto.RequestDTO;
import com.example.service.RequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/requests")
@RestController
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping
    public List<RequestDTO> getAll() {
        return requestService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> getById(@PathVariable Integer id) {
        RequestDTO byId = requestService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<RequestDTO> createRequest(@Valid @RequestBody RequestDTO RequestDTO) {
        RequestDTO catalog = requestService.createRequest(RequestDTO);
        return new ResponseEntity<>(catalog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestDTO> updateRequest(
            @PathVariable Integer id,
            @Valid @RequestBody RequestDTO RequestDTO
    ) {
        RequestDTO updateBookCatalog = requestService.updateRequest(id, RequestDTO);
        return ResponseEntity.ok(updateBookCatalog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Integer id) {
        requestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

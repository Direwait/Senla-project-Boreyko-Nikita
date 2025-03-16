package com.example.service;

import com.example.dto.BookCatalogDTO;
import com.example.mapper.BookCatalogMapper;
import com.example.model.BookCatalog;
import com.example.repository.BookCatalogRepository;
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
public class BookCatalogService {
    private final BookCatalogRepository bookCatalogRepository;
    private final BookCatalogMapper bookCatalogMapper;

    @Transactional
    public BookCatalogDTO getById(Integer id) {
        BookCatalog bookCatalog = bookCatalogRepository.findById(id)
                .orElseThrow(
                        () -> {
                            log.error("BookCatalog with ID {} dont found", id);
                            return new EntityNotFoundException(" BookCatalog with ID " + id + " dont found");
                        });

        log.info("Successfully retrieved BookCatalog ID: {}", id);
        return bookCatalogMapper.modelToDTO(bookCatalog);
    }

    public List<BookCatalogDTO> getAll() {
        List<BookCatalogDTO> bookCatalogDTOS = bookCatalogRepository
                .findAll()
                .stream()
                .map(bookCatalogMapper::modelToDTO)
                .collect(Collectors.toList());

        log.info("Found {} bookCatalogs", bookCatalogDTOS.size());
        return bookCatalogDTOS;
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!bookCatalogRepository.existsById(id)) {
            log.error("Delete failed: BookCatalog with ID {} not found", id);
            throw new EntityNotFoundException("BookCatalog with ID " + id + " not found");
        }

        bookCatalogRepository.deleteById(id);
        log.info("Successfully deleted BookCatalog with ID: {}", id);
    }

    @Transactional
    public BookCatalogDTO createBookCatalog(BookCatalogDTO bookCatalogDTO) {
        BookCatalog bookCatalog = bookCatalogMapper.dtoToModel(bookCatalogDTO);
        BookCatalog savedBookCatalog = bookCatalogRepository.save(bookCatalog);

        log.info("Successfully created Book ID: {}", savedBookCatalog.getBookCatalogId());
        return bookCatalogMapper.modelToDTO(savedBookCatalog);
    }

    @Transactional
    public BookCatalogDTO updateBookCatalog(Integer id, BookCatalogDTO bookCatalogDTO) {
        BookCatalog bookCatalog = bookCatalogRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("BookCatalog with ID {} dont found ", id);
                            return new EntityNotFoundException("BookCatalog with ID " + id + " dont found");
                        }
                );
        bookCatalogMapper.updateBookCatalogFromDTO(bookCatalogDTO, bookCatalog);
        BookCatalog updatedBookCatalog = bookCatalogRepository.save(bookCatalog);

        log.info("Successfully updated BookCatalog ID: {} - {}", id, updatedBookCatalog.getBookCatalogId());
        return bookCatalogMapper.modelToDTO(updatedBookCatalog);
    }
}
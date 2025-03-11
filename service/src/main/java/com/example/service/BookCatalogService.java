package com.example.service;

import com.example.dto.BookCatalogDTO;
import com.example.mapper.BookCatalogMapper;
import com.example.model.BookCatalog;
import com.example.repository.BookCatalogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookCatalogService {
    private final BookCatalogRepository bookCatalogRepository;
    private final BookCatalogMapper bookCatalogMapper;

    @Transactional
    public BookCatalogDTO getById(Integer id) {
        BookCatalog bookCatalog = bookCatalogRepository.findById(id).orElseThrow();
        return bookCatalogMapper.modelToDTO(bookCatalog);
    }

    public List<BookCatalogDTO> getAll() {
        return bookCatalogRepository.findAll().stream()
                .map(bookCatalogMapper::modelToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        bookCatalogRepository.deleteById(id);
    }

    @Transactional
    public BookCatalogDTO createBookCatalog(BookCatalogDTO bookCatalogDTO) {
        BookCatalog bookCatalog = bookCatalogMapper.dtoToModel(bookCatalogDTO);
        BookCatalog savedBookCatalog = bookCatalogRepository.save(bookCatalog);
        return bookCatalogMapper.modelToDTO(savedBookCatalog);
    }

    @Transactional
    public BookCatalogDTO updateBookCatalog(Integer id, BookCatalogDTO bookCatalogDTO) {
        BookCatalog bookCatalog = bookCatalogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookCatalog с ID " + id + " не найдена"));
        bookCatalogMapper.updateBookCatalogFromDTO(bookCatalogDTO, bookCatalog);
        BookCatalog updatedBookCatalog = bookCatalogRepository.save(bookCatalog);

        return bookCatalogMapper.modelToDTO(updatedBookCatalog);
    }
}
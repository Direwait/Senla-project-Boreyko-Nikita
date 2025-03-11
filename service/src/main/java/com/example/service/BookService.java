package com.example.service;

import com.example.dto.BookDTO;
import com.example.mapper.BookMapper;
import com.example.model.Book;
import com.example.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookDTO getById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return bookMapper.modelToDTO(book); // Возвращайте Response DTO
    }

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::modelToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.dtoToModel(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.modelToDTO(savedBook);
    }

    @Transactional
    public BookDTO updateBook(Integer id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с ID " + id + " не найдена"));
        bookMapper.updateBookFromDTO(bookDTO, book);
        Book updatedBook = bookRepository.save(book);

        return bookMapper.modelToDTO(updatedBook);
    }
}
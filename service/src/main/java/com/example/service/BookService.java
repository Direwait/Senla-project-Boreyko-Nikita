package com.example.service;

import com.example.dto.BookDTO;
import com.example.mapper.BookMapper;
import com.example.model.Book;
import com.example.repository.BookRepository;
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
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    @Transactional
    public BookDTO getById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> {
                            log.error("Book with ID {} dont found ", id);
                            return new EntityNotFoundException("Book with ID " + id + " dont found");
                        });

        log.info("Successfully retrieved Book ID: {}", id);
        return bookMapper.modelToDTO(book);
    }

    public List<BookDTO> getAll() {
        List<BookDTO> bookDTOS = bookRepository
                .findAll()
                .stream()
                .map(bookMapper::modelToDTO)
                .collect(Collectors.toList());

        log.info("Found {} books", bookDTOS.size());
        return bookDTOS;
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!bookRepository.existsById(id)) {
            log.error("Delete failed: Book with ID {} not found", id);
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }

        bookRepository.deleteById(id);
        log.info("Successfully deleted Book with ID: {}", id);
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.dtoToModel(bookDTO);
        Book savedBook = bookRepository.save(book);

        log.info("Successfully created Book ID: {} - {}", savedBook.getBookId(), savedBook.getBookTitle());
        return bookMapper.modelToDTO(savedBook);
    }

    @Transactional
    public BookDTO updateBook(Integer id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> {
                            log.error("Book with ID {} dont found", id);
                            return new EntityNotFoundException("Book with ID " + id + " dont found");
                        });
        bookMapper.updateBookFromDTO(bookDTO, book);
        Book updatedBook = bookRepository.save(book);

        log.info("Successfully updated Book ID: {} - {}", id, updatedBook.getBookTitle());
        return bookMapper.modelToDTO(updatedBook);
    }
}

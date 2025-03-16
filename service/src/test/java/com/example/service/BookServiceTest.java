package com.example.service;

import com.example.dto.BookDTO;
import com.example.mapper.BookMapper;
import com.example.model.Book;
import com.example.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @Test
    void testGetById() {
        Integer id = 1;
        Book book = new Book();
        BookDTO expectedDTO = new BookDTO();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.modelToDTO(book)).thenReturn(expectedDTO);

        BookDTO result = bookService.getById(id);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(bookRepository).findById(id);
        verify(bookMapper).modelToDTO(book);
    }


    @Test
    void getAllTest() {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.modelToDTO(any(Book.class))).thenReturn(new BookDTO());

        List<BookDTO> result = bookService.getAll();

        Assertions.assertFalse(result.isEmpty());
        verify(bookRepository).findAll();
    }

    @Test
    void testDeleteById() {
        Integer requestId = 1;
        when(bookRepository.existsById(requestId)).thenReturn(true);

        bookService.deleteById(requestId);

        verify(bookRepository).existsById(requestId);
        verify(bookRepository).deleteById(requestId);
    }

    @Test
    void testCreateBook() {
        BookDTO requestDTO = new BookDTO();
        Book book = new Book();
        Book savedBook = new Book();
        BookDTO responseDTO = new BookDTO();

        when(bookMapper.dtoToModel(requestDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.modelToDTO(savedBook)).thenReturn(responseDTO);

        BookDTO result = bookService.createBook(requestDTO);

        assertEquals(responseDTO, result);
        verify(bookMapper).dtoToModel(requestDTO);
        verify(bookRepository).save(book);
        verify(bookMapper).modelToDTO(savedBook);
    }

    @Test
    void testUpdateBook() {
        var book = new Book();
        book.setBookId(1);
        book.setBookTitle("Old Title");

        var bookDTO = new BookDTO();
        bookDTO.setBookTitle("New Title");

        var updatedBook = new Book();
        updatedBook.setBookTitle("New Title");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookMapper.modelToDTO(any(Book.class))).thenReturn(bookDTO);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        BookDTO updatedBookDTO = bookService.updateBook(1, bookDTO);

        assertNotNull(updatedBookDTO);
        assertEquals("New Title", updatedBookDTO.getBookTitle());

        verify(bookRepository).findById(1);
        verify(bookMapper).updateBookFromDTO(bookDTO, book);
        verify(bookRepository).save(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }
}
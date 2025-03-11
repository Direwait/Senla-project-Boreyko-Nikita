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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @Test
    void getByIdTest() {
        Integer id = 1;
        Book book = new Book();
        BookDTO expectedDTO = new BookDTO();

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.modelToDTO(book)).thenReturn(expectedDTO);

        BookDTO result = bookService.getById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDTO, result);
        Mockito.verify(bookRepository).findById(id);
        Mockito.verify(bookMapper).modelToDTO(book);
    }


    @Test
    void getAllTest() {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> books = List.of(book1, book2);

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(bookMapper.modelToDTO(Mockito.any(Book.class))).thenReturn(new BookDTO());

        List<BookDTO> result = bookService.getAll();

        Assertions.assertFalse(result.isEmpty());
        Mockito.verify(bookRepository).findAll();
    }

    @Test
    void deleteByIdTest() {
        Integer id = 1;

        bookService.deleteById(id);

        Mockito.verify(bookRepository).deleteById(id);
    }

    @Test
    void createBookTest() {
        BookDTO requestDTO = new BookDTO();
        Book book = new Book();
        Book savedBook = new Book();
        BookDTO responseDTO = new BookDTO();

        Mockito.when(bookMapper.dtoToModel(requestDTO)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(savedBook);
        Mockito.when(bookMapper.modelToDTO(savedBook)).thenReturn(responseDTO);

        BookDTO result = bookService.createBook(requestDTO);

        Assertions.assertEquals(responseDTO, result);
        Mockito.verify(bookMapper).dtoToModel(requestDTO);
        Mockito.verify(bookRepository).save(book);
        Mockito.verify(bookMapper).modelToDTO(savedBook);
    }

    @Test
    void updateBookTest() {
        var book = new Book();
        book.setBookId(1);
        book.setBookTitle("Old Title");

        var bookDTO = new BookDTO();
        bookDTO.setBookTitle("New Title");

        var updatedBook = new Book();
        updatedBook.setBookTitle("New Title");

        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.modelToDTO(Mockito.any(Book.class))).thenReturn(bookDTO);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        BookDTO updatedBookDTO = bookService.updateBook(1, bookDTO);

        Assertions.assertNotNull(updatedBookDTO);
        Assertions.assertEquals("New Title", updatedBookDTO.getBookTitle());

        Mockito.verify(bookRepository).findById(1);
        Mockito.verify(bookMapper).updateBookFromDTO(bookDTO, book);
        Mockito.verify(bookRepository).save(book);
        Mockito.verifyNoMoreInteractions(bookRepository, bookMapper);
    }
}
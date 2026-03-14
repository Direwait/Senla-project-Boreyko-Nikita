package com.example.service;

import com.example.dto.BookCatalogDTO;
import com.example.mapper.BookCatalogMapper;
import com.example.model.BookCatalog;
import com.example.repository.BookCatalogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookCatalogServiceTest {

    @InjectMocks
    private BookCatalogService bookCatalogService;
    @Mock
    private BookCatalogRepository bookCatalogRepository;
    @Mock
    private BookCatalogMapper bookCatalogMapper;

    @Test
    void testGetById() {
        Integer id = 1;
        BookCatalog book = new BookCatalog();
        BookCatalogDTO expectedDTO = new BookCatalogDTO();

        when(bookCatalogRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookCatalogMapper.modelToDTO(book)).thenReturn(expectedDTO);

        BookCatalogDTO result = bookCatalogService.getById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDTO, result);
        verify(bookCatalogRepository).findById(id);
        verify(bookCatalogMapper).modelToDTO(book);
    }


    @Test
    void testGetAll() {
        BookCatalog book1 = new BookCatalog();
        BookCatalog book2 = new BookCatalog();
        List<BookCatalog> books = List.of(book1, book2);

        when(bookCatalogRepository.findAll()).thenReturn(books);
        when(bookCatalogMapper.modelToDTO(any(BookCatalog.class)))
                .thenReturn(new BookCatalogDTO());

        List<BookCatalogDTO> result = bookCatalogService.getAll();

        Assertions.assertFalse(result.isEmpty());
        verify(bookCatalogRepository).findAll();
    }

    @Test
    void testDeleteById() {
        Integer requestId = 1;
        when(bookCatalogRepository.existsById(requestId)).thenReturn(true);

        bookCatalogService.deleteById(requestId);

        verify(bookCatalogRepository).existsById(requestId);
        verify(bookCatalogRepository).deleteById(requestId);
    }

    @Test
    void testCreateBookCatalog() {
        BookCatalogDTO requestDTO = new BookCatalogDTO();
        BookCatalog book = new BookCatalog();
        BookCatalog savedBook = new BookCatalog();
        BookCatalogDTO responseDTO = new BookCatalogDTO();

        when(bookCatalogMapper.dtoToModel(requestDTO)).thenReturn(book);
        when(bookCatalogRepository.save(book)).thenReturn(savedBook);
        when(bookCatalogMapper.modelToDTO(savedBook)).thenReturn(responseDTO);

        BookCatalogDTO result = bookCatalogService.createBookCatalog(requestDTO);

        Assertions.assertEquals(responseDTO, result);
        verify(bookCatalogMapper).dtoToModel(requestDTO);
        verify(bookCatalogRepository).save(book);
        verify(bookCatalogMapper).modelToDTO(savedBook);
    }

    @Test
    void updateBookCatalogTest() {
        var bookCatalog = new BookCatalog();
        bookCatalog.setBookCatalogId(1);

        var bookCatalogDTO = new BookCatalogDTO();

        var updatedBookCatalog = new BookCatalog();

        when(bookCatalogRepository.findById(1)).thenReturn(Optional.of(bookCatalog));
        when(bookCatalogMapper.modelToDTO(any(BookCatalog.class))).thenReturn(bookCatalogDTO);
        when(bookCatalogRepository.save(any(BookCatalog.class))).thenReturn(updatedBookCatalog);

        BookCatalogDTO updatedBookDTO = bookCatalogService.updateBookCatalog(1, bookCatalogDTO);

        Assertions.assertNotNull(updatedBookDTO);

        verify(bookCatalogRepository).findById(1);
        verify(bookCatalogMapper).updateBookCatalogFromDTO(bookCatalogDTO, bookCatalog);
        verify(bookCatalogRepository).save(bookCatalog);
        verifyNoMoreInteractions(bookCatalogRepository, bookCatalogMapper);
    }
}
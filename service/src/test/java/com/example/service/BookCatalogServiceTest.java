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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class BookCatalogServiceTest {

    @Mock
    private BookCatalogRepository bookCatalogRepository;
    @Mock
    private BookCatalogMapper bookCatalogMapper;
    @InjectMocks
    private BookCatalogService bookCatalogService;

    @Test
    void getByIdTest() {
        Integer id = 1;
        BookCatalog book = new BookCatalog();
        BookCatalogDTO expectedDTO = new BookCatalogDTO();

        Mockito.when(bookCatalogRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookCatalogMapper.modelToDTO(book)).thenReturn(expectedDTO);

        BookCatalogDTO result = bookCatalogService.getById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDTO, result);
        Mockito.verify(bookCatalogRepository).findById(id);
        Mockito.verify(bookCatalogMapper).modelToDTO(book);
    }


    @Test
    void getAllTest() {
        BookCatalog book1 = new BookCatalog();
        BookCatalog book2 = new BookCatalog();
        List<BookCatalog> books = List.of(book1, book2);

        Mockito.when(bookCatalogRepository.findAll()).thenReturn(books);
        Mockito.when(bookCatalogMapper.modelToDTO(Mockito.any(BookCatalog.class)))
                .thenReturn(new BookCatalogDTO());

        List<BookCatalogDTO> result = bookCatalogService.getAll();

        Assertions.assertFalse(result.isEmpty());
        Mockito.verify(bookCatalogRepository).findAll();
    }

    @Test
    void deleteByIdTest() {
        bookCatalogService.deleteById(Mockito.anyInt());

        Mockito.verify(bookCatalogRepository).deleteById(Mockito.anyInt());
    }

    @Test
    void createBookTest() {
        BookCatalogDTO requestDTO = new BookCatalogDTO();
        BookCatalog book = new BookCatalog();
        BookCatalog savedBook = new BookCatalog();
        BookCatalogDTO responseDTO = new BookCatalogDTO();

        Mockito.when(bookCatalogMapper.dtoToModel(requestDTO)).thenReturn(book);
        Mockito.when(bookCatalogRepository.save(book)).thenReturn(savedBook);
        Mockito.when(bookCatalogMapper.modelToDTO(savedBook)).thenReturn(responseDTO);

        BookCatalogDTO result = bookCatalogService.createBookCatalog(requestDTO);

        Assertions.assertEquals(responseDTO, result);
        Mockito.verify(bookCatalogMapper).dtoToModel(requestDTO);
        Mockito.verify(bookCatalogRepository).save(book);
        Mockito.verify(bookCatalogMapper).modelToDTO(savedBook);
    }

    @Test
    void updateBookCatalogTest() {
        var bookCatalog = new BookCatalog();
        bookCatalog.setBookCatalogId(1);

        var bookCatalogDTO = new BookCatalogDTO();

        var updatedBookCatalog = new BookCatalog();

        Mockito.when(bookCatalogRepository.findById(1)).thenReturn(Optional.of(bookCatalog));
        Mockito.when(bookCatalogMapper.modelToDTO(Mockito.any(BookCatalog.class))).thenReturn(bookCatalogDTO);
        Mockito.when(bookCatalogRepository.save(Mockito.any(BookCatalog.class))).thenReturn(updatedBookCatalog);

        BookCatalogDTO updatedBookDTO = bookCatalogService.updateBookCatalog(1, bookCatalogDTO);

        Assertions.assertNotNull(updatedBookDTO);

        Mockito.verify(bookCatalogRepository).findById(1);
        Mockito.verify(bookCatalogMapper).updateBookCatalogFromDTO(bookCatalogDTO, bookCatalog);
        Mockito.verify(bookCatalogRepository).save(bookCatalog);
        Mockito.verifyNoMoreInteractions(bookCatalogRepository, bookCatalogMapper);
    }
}
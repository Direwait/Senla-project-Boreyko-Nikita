package com.example.service;

import com.example.dto.RentalDTO;
import com.example.mapper.RentalMapper;
import com.example.model.Book;
import com.example.model.Rental;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.RentalRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    @InjectMocks
    RentalService rentalService;
    @Mock
    RentalRepository rentalRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    RentalMapper rentalMapper;
    @Mock
    UserRepository userRepository;

    @Test
    void testGetById() {
        var entity = new Rental();
        var dto = new RentalDTO();

        Mockito.when(rentalRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        Mockito.when(rentalMapper.modelToDTO(entity)).thenReturn(dto);

        var byId = rentalService.getById(1);

        assertNotNull(byId);
        assertEquals(dto, byId);
    }

    @Test
    void testGetAll() {
        var entity = new Rental();
        var dto = new RentalDTO();

        Mockito.when(rentalRepository.findAll()).thenReturn(List.of(entity));
        Mockito.when(rentalMapper.modelToDTO(entity)).thenReturn(dto);

        List<RentalDTO> all = rentalService.getAll();

        Assertions.assertFalse(all.isEmpty());
        assertEquals(1, all.size());
        assertEquals(dto, all.getFirst());
    }

    @Test
    void testDeleteById() {
        Integer requestId = 1;
        when(rentalRepository.existsById(requestId)).thenReturn(true);

        rentalService.deleteById(requestId);

        verify(rentalRepository).existsById(requestId);
        verify(rentalRepository).deleteById(requestId);
    }

    @Test
    void testUpdateDto() {
        var rental = new Rental();
        rental.setRentalId(1);

        var rentalDTO = new RentalDTO();

        var updatedRental = new Rental();

        Mockito.when(rentalRepository.findById(1)).thenReturn(Optional.of(rental));
        Mockito.when(rentalMapper.modelToDTO(any(Rental.class))).thenReturn(rentalDTO);
        Mockito.when(rentalRepository.save(any(Rental.class))).thenReturn(updatedRental);

        RentalDTO updatedBookDTO = rentalService.updateRental(1, rentalDTO);

        assertNotNull(updatedBookDTO);

        Mockito.verify(rentalRepository).findById(1);
        Mockito.verify(rentalMapper).updateRentalFromDTO(rentalDTO, rental);
        Mockito.verify(rentalRepository).save(rental);
        Mockito.verifyNoMoreInteractions(rentalRepository, rentalMapper);
    }

    @Test
    void testCreateRental() {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setBookId(1);

        Book book = new Book();
        book.setBookId(1);
        book.setBookQuantity(3);
        book.setBookAvailable(false);  // Книга недоступна

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        setupCurrentUser();

        assertNotNull(rentalDTO);
        assertThatThrownBy(() -> rentalService.createRental(rentalDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Book not available");

        verify(bookRepository, never()).save(any());
        verify(rentalRepository, never()).save(any());
    }

    @Test
    void testGetOverdueRentals() {
        Rental rental = new Rental();
        RentalDTO rentalDTO = new RentalDTO();
        List<Rental> rentals = List.of(rental, rental);

        when(rentalRepository.findOverdueRentals()).thenReturn(rentals);
        when(rentalMapper.modelToDTO(any(Rental.class))).thenReturn(rentalDTO);

        List<RentalDTO> result = rentalService.getOverdueRentals();

        assertEquals(2, result.size());
        verify(rentalRepository).findOverdueRentals();
        verify(rentalMapper, times(2)).modelToDTO(any(Rental.class));
    }

    private void setupCurrentUser() {
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "pass");
        SecurityContextHolder.getContext().setAuthentication(auth);
        lenient().when(userRepository.findByUserUsername("user"))
                .thenReturn(Optional.of(new User()));
    }
}

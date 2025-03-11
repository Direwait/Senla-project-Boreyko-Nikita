package com.example.service;

import com.example.dto.RentalDTO;
import com.example.mapper.RentalMapper;
import com.example.model.Rental;
import com.example.repository.RentalRepository;
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
public class RentalServiceTest {

    @InjectMocks
    RentalService rentalService;
    @Mock
    RentalRepository rentalRepository;
    @Mock
    RentalMapper rentalMapper;

    @Test
    void testGetById() {
        var entity = new Rental();
        var dto = new RentalDTO();

        Mockito.when(rentalRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        Mockito.when(rentalMapper.modelToDTO(entity)).thenReturn(dto);

        var byId = rentalService.getById(1);

        Assertions.assertNotNull(byId);
        Assertions.assertEquals(dto, byId);
    }

    @Test
    void testGetAll() {
        var entity = new Rental();
        var dto = new RentalDTO();

        Mockito.when(rentalRepository.findAll()).thenReturn(List.of(entity));
        Mockito.when(rentalMapper.modelToDTO(entity)).thenReturn(dto);

        List<RentalDTO> all = rentalService.getAll();

        Assertions.assertFalse(all.isEmpty());
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(dto, all.get(0));
    }

    @Test
    void testDeleteById() {
        rentalService.deleteById(Mockito.anyInt());

        Mockito.verify(rentalRepository).deleteById(Mockito.anyInt());
    }

    @Test
    void testCreateDto() {
        var entity = new Rental();
        var dto = new RentalDTO();

        Mockito.when(rentalMapper.dtoToModel(dto)).thenReturn(entity);
        Mockito.when(rentalRepository.save(entity)).thenReturn(entity);
        Mockito.when(rentalMapper.modelToDTO(entity)).thenReturn(dto);

        var result = rentalService.createRental(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void testUpdateDto() {
        var rental = new Rental();
        rental.setRentalId(1);

        var rentalDTO = new RentalDTO();

        var updatedRental = new Rental();

        Mockito.when(rentalRepository.findById(1)).thenReturn(Optional.of(rental));
        Mockito.when(rentalMapper.modelToDTO(Mockito.any(Rental.class))).thenReturn(rentalDTO);
        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(updatedRental);

        RentalDTO updatedBookDTO = rentalService.updateRental(1, rentalDTO);

        Assertions.assertNotNull(updatedBookDTO);

        Mockito.verify(rentalRepository).findById(1);
        Mockito.verify(rentalMapper).updateRentalFromDTO(rentalDTO, rental);
        Mockito.verify(rentalRepository).save(rental);
        Mockito.verifyNoMoreInteractions(rentalRepository, rentalMapper);
    }
}

package com.example.service;

import com.example.dto.RentalDTO;
import com.example.mapper.RentalMapper;
import com.example.model.Rental;
import com.example.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Transactional
    public RentalDTO getById(Integer id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        return rentalMapper.modelToDTO(rental);
    }

    public List<RentalDTO> getAll() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::modelToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        rentalRepository.deleteById(id);
    }

    @Transactional
    public RentalDTO createRental(RentalDTO catalogDTO) {
        Rental rental = rentalMapper.dtoToModel(catalogDTO);
        Rental save = rentalRepository.save(rental);
        return rentalMapper.modelToDTO(save);
    }

    @Transactional
    public RentalDTO updateRental(Integer id, RentalDTO rentalDTO) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Аренда с ID " + id + " не найдена"));
        rentalMapper.updateRentalFromDTO(rentalDTO, rental);
        Rental updatedRental = rentalRepository.save(rental);

        return rentalMapper.modelToDTO(updatedRental);
    }


}

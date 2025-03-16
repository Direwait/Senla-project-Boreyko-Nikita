package com.example.service;

import com.example.dto.RentalDTO;
import com.example.mapper.RentalMapper;
import com.example.model.Book;
import com.example.model.Rental;
import com.example.model.User;
import com.example.model.enums.RentalStatus;
import com.example.repository.BookRepository;
import com.example.repository.RentalRepository;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper;

    @Transactional
    public RentalDTO getById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Rental with ID {} dont found", id);
                    return new EntityNotFoundException("Rental with ID " + id + " dont found");
                });

        log.info("Successfully retrieved rental ID: {}", id);
        return rentalMapper.modelToDTO(rental);
    }

    public List<RentalDTO> getAll() {
        List<RentalDTO> rentalDTOS = rentalRepository.findAll().stream()
                .map(rentalMapper::modelToDTO)
                .collect(Collectors.toList());

        log.info("Found {} all rentals", rentalDTOS.size());
        return rentalDTOS;
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!rentalRepository.existsById(id)) {
            log.error("Delete failed: Catalog with ID {} not found", id);
            throw new EntityNotFoundException("Catalog with ID " + id + " not found");
        }

        rentalRepository.deleteById(id);
        log.info("Successfully deleted Catalog with ID: {}", id);
    }


    @Transactional
    public RentalDTO updateRental(Integer id, RentalDTO rentalDTO) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Catalog with ID {} dont found", id);
                    return new EntityNotFoundException("Rental with ID " + id + " dont found");
                });

        rentalMapper.updateRentalFromDTO(rentalDTO, rental);
        Rental updatedRental = rentalRepository.save(rental);

        log.info("Successfully updated Book ID: {} - {}", id, updatedRental.getRentalId());
        return rentalMapper.modelToDTO(updatedRental);
    }

    @Transactional
    public RentalDTO createRental(RentalDTO rentalDTO) {
        Book book = bookRepository.findById(rentalDTO.getBookId())
                .orElseThrow(() ->{
                    log.error("Book not found");
                    return new EntityNotFoundException("Book not found");
                });

        User user = getCurrentUser();

        if (book.getBookQuantity() < 1 || !book.isBookAvailable()) {
            log.error("Book not available");
            throw new IllegalStateException("Book not available");
        }

        Rental rental = Rental.builder()
                .user(user)
                .book(book)
                .rentalDate(new Date())
                .rentalReturnDate(rentalDTO.getRentalReturnDate())
                .rentalStatus(RentalStatus.ACTIVE)
                .build();

        book.setBookQuantity(book.getBookQuantity() - 1);
        bookRepository.save(book);
        rentalRepository.save(rental);

        log.info("Successfully created Rental ID: {} ", rental.getRentalId());
        return rentalMapper.modelToDTO(rental);
    }

    @Transactional
    public RentalDTO returnBook(Integer rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> {
                            log.error("Rental not found");
                            return new EntityNotFoundException("Rental not found");
                        });
        if (rental.getRentalStatus() == RentalStatus.RETURNED) {
            log.error("Book already returned");
            throw new IllegalStateException("Book already returned");
        }

        rental.setRentalActualReturnDate(new Date());
        rental.setRentalStatus(RentalStatus.RETURNED);

        Book book = rental.getBook();
        book.setBookQuantity(book.getBookQuantity() + 1);
        bookRepository.save(book);
        rentalRepository.save(rental);

        log.info("Successfully processed return for rental ID: {}. Book ID: {}, User ID: {}",
                rentalId, book.getBookId(), rental.getUser().getUserId());
        return rentalMapper.modelToDTO(rental);
    }

    public List<RentalDTO> getOverdueRentals() {
        var rentalDTOS = rentalRepository.findOverdueRentals().stream()
                .map(rentalMapper::modelToDTO)
                .toList();

        log.info("Found {} overdue rentals", rentalDTOS.size());
        return rentalDTOS;
    }

    public List<RentalDTO> getActiveRentals() {
        List<RentalDTO> rentalDTOS = rentalRepository.findByRentalStatus(RentalStatus.ACTIVE).stream()
                .map(rentalMapper::modelToDTO)
                .toList();

        log.info("Found {} active rentals", rentalDTOS.size());
        return rentalDTOS;
    }

    public List<RentalDTO> getReturnedRentals() {
        List<RentalDTO> rentalDTOS = rentalRepository.findByRentalStatus(RentalStatus.RETURNED).stream()
                .map(rentalMapper::modelToDTO)
                .toList();

        log.info("Found {} returned rentals", rentalDTOS.size());
        return rentalDTOS;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserUsername(authentication.getName())
                .orElseThrow(
                        () -> {
                            log.error("User not found in security context");
                            return new EntityNotFoundException("User not found in security context");
                        });
    }
}

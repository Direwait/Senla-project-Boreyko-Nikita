package com.example.repository;


import com.example.model.Rental;
import com.example.model.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query("SELECT r FROM Rental r WHERE r.rentalStatus = 'ACTIVE' AND r.rentalReturnDate < CURRENT_DATE")
    List<Rental> findOverdueRentals();

    @Query("SELECT r FROM Rental r WHERE r.rentalStatus = :status")
    List<Rental> findByRentalStatus(@Param("status") RentalStatus status);
}

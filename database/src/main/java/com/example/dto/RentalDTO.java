package com.example.dto;

import com.example.model.enums.RentalStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RentalDTO {

    private int rentalId;

    private Integer userId;

    @NotNull(message = "Book ID cannot be null")
    private Integer bookId;

    private RentalStatus rentalStatus;

    private Date rentalDate;

    @Future(message = "Rental return date must be future")
    private Date rentalReturnDate;

    private Date rentalActualReturnDate;
}

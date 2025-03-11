package com.example.dto;

import com.example.model.Book;
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

    @NotNull(message = "BookPublication cannot be null")
    private UserDTO user;

    @NotNull(message = "Book cannot be null")
    private Book book;

    @NotNull(message = "rentalDate cannot be null")
    private Date rentalDate;

    @NotNull(message = "returnDate cannot be null")
    private Date returnDate;

    @NotNull(message = "actual cannot be null")
    private Date actualReturnDate;
}

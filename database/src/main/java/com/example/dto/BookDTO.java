package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDTO {
    private int bookId;

    @NotEmpty(message = "BookTitle cannot be empty")
    private String bookTitle;

    @NotEmpty(message = "BookAuthor cannot be empty")
    private String bookAuthor;

    @NotNull(message = "BookPublication cannot be null")
    private Integer bookPublication;

    @NotEmpty(message = "BookIsbn cannot be empty")
    private String bookIsbn;

    @NotEmpty(message = "BookIsbn cannot be empty")
    private String bookDescription;

    private boolean bookAvailable;

    @NotNull(message = "BookPublication cannot be null")
    private int bookQuantity;
}


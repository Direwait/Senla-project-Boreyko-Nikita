package com.example.dto;

import com.example.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookCatalogDTO {
    private int bookCatalogId;
    private Book book;
    private CatalogDTO catalog;
}

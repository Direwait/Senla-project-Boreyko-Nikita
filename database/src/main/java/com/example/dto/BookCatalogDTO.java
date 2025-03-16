package com.example.dto;

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
    private BookDTO book;
    private CatalogDTO catalog;
}

package com.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CatalogDTO {

    private int catalogId;

    @NotEmpty(message = "catalogTitle cannot be empty")
    private String catalogTitle;

    private CatalogDTO catalogParent;
}

package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CatalogDTO {

    private int catalogId;

    @NotEmpty(message = "catalogTitle cannot be empty")
    private String catalogTitle;

    private Integer parentCatalogId;

    private List<CatalogDTO> subCatalogs;

}

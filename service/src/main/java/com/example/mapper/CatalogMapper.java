package com.example.mapper;

import com.example.dto.CatalogDTO;
import com.example.model.Catalog;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

    @Mapping(target = "parentCatalogId", source = "catalogParent.catalogId")
    @Mapping(target = "subCatalogs", qualifiedByName = "mapSubCatalogs")
    CatalogDTO modelToDTO(Catalog catalog);

    @Mapping(target = "catalogParent", ignore = true)
    @Mapping(target = "subCatalogs", ignore = true)
    @Mapping(target = "books", ignore = true)
    Catalog dtoToModel(CatalogDTO catalogDTO);

    @Mapping(target = "catalogId", ignore = true)
    @Mapping(target = "catalogParent", ignore = true)
    @Mapping(target = "subCatalogs", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateCatalogFromDTO(CatalogDTO catalogDTO, @MappingTarget Catalog catalog);

    @Named("mapSubCatalogs")
    default List<CatalogDTO> mapSubCatalogs(List<Catalog> subCatalogs) {
        if (subCatalogs == null) return Collections.emptyList();
        return subCatalogs.stream()
                .map(this::modelToDTO)
                .toList();
    }

    default Integer mapParentCatalog(Catalog catalogParent) {
        return catalogParent != null ? catalogParent.getCatalogId() : null;
    }
}
package com.example.mapper;

import com.example.dto.CatalogDTO;
import com.example.model.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CatalogMapper {


    CatalogDTO modelToDTO(Catalog catalog);

    Catalog dtoToModel(CatalogDTO catalogDTO);

    @Mapping(target = "catalogId", ignore = true)

    void updateCatalogFromDTO(CatalogDTO catalogDTO, @MappingTarget Catalog catalog);
}

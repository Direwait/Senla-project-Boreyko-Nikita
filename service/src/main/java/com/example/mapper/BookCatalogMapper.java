package com.example.mapper;

import com.example.dto.BookCatalogDTO;
import com.example.model.BookCatalog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface BookCatalogMapper {


    BookCatalogDTO modelToDTO(BookCatalog bookCatalog);

    BookCatalog dtoToModel(BookCatalogDTO bookCatalogDTO);

    @Mapping(target = "bookCatalogId", ignore = true)
    void updateBookCatalogFromDTO(BookCatalogDTO bookCatalogDTO, @MappingTarget BookCatalog bookCatalog);
}

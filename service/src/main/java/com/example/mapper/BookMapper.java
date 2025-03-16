package com.example.mapper;

import com.example.dto.BookDTO;
import com.example.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO modelToDTO(Book book);

    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "catalogs", ignore = true)
    Book dtoToModel(BookDTO bookDTO);

    @Mapping(target = "bookId", ignore = true)
    void updateBookFromDTO(BookDTO bookDTO, @MappingTarget Book book);
}

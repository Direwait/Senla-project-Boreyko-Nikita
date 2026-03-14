package com.example.mapper;

import com.example.dto.RentalDTO;
import com.example.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "bookId", source = "book.bookId")
    RentalDTO modelToDTO(Rental rental);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Rental dtoToModel(RentalDTO rentalDTO);

    @Mapping(target = "rentalId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    void updateRentalFromDTO(RentalDTO rentalDTO, @MappingTarget Rental rental);
}

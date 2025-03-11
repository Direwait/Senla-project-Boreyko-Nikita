package com.example.mapper;

import com.example.dto.RentalDTO;
import com.example.model.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RentalMapper {


    RentalDTO modelToDTO(Rental rental);

    Rental dtoToModel(RentalDTO rentalDTO);

    @Mapping(target = "rentalId", ignore = true)
    void updateRentalFromDTO(RentalDTO rentalDTO, @MappingTarget Rental rental);
}

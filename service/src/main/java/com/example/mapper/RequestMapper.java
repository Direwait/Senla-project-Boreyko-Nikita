package com.example.mapper;

import com.example.dto.RequestDTO;
import com.example.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "bookId", source = "book.bookId")
    @Mapping(target = "requestDate", source = "requestDate")
    RequestDTO modelToDTO(Request request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Request dtoToModel(RequestDTO requestDTO);

    @Mapping(target = "requestId", ignore = true)
    void updateRequestFromDTO(RequestDTO requestDTO, @MappingTarget Request request);
}

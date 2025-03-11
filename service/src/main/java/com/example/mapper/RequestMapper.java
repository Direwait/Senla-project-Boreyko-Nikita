package com.example.mapper;

import com.example.dto.RequestDTO;
import com.example.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestMapper {


    RequestDTO modelToDTO(Request request);

    Request dtoToModel(RequestDTO requestDTO);

    @Mapping(target = "requestId", ignore = true)
    void updateRequestFromDTO(RequestDTO requestDTO, @MappingTarget Request request);
}

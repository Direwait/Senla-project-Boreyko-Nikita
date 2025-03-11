package com.example.mapper;


import com.example.dto.UserDTO;
import com.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO modelToDTO(User user);

    User dtoToModel(UserDTO userDTO);

    @Mapping(target = "userId", ignore = true)
    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);
}

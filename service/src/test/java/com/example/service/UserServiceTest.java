package com.example.service;

import com.example.dto.UserDTO;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserMapper userMapper;

    @Test
    void testGetById() {
        var entity = new User();
        var dto = new UserDTO();

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        Mockito.when(userMapper.modelToDTO(entity)).thenReturn(dto);

        var byId = userService.getById(1);

        Assertions.assertNotNull(byId);
        Assertions.assertEquals(dto, byId);
    }

    @Test
    void testGetAll() {
        var entity = new User();
        var dto = new UserDTO();

        Mockito.when(userRepository.findAll()).thenReturn(List.of(entity));
        Mockito.when(userMapper.modelToDTO(entity)).thenReturn(dto);

        List<UserDTO> all = userService.getAll();

        Assertions.assertFalse(all.isEmpty());
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(dto, all.get(0));
    }

    @Test
    void testDeleteById() {
        userService.deleteById(Mockito.anyInt());

        Mockito.verify(userRepository).deleteById(Mockito.anyInt());
    }

    @Test
    void testCreateUser() {
        var entity = new User();
        var dto = new UserDTO();

        Mockito.when(userMapper.dtoToModel(dto)).thenReturn(entity);
        Mockito.when(userRepository.save(entity)).thenReturn(entity);
        Mockito.when(userMapper.modelToDTO(entity)).thenReturn(dto);

        var result = userService.createUser(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void testUpdateUser() {
    }
}

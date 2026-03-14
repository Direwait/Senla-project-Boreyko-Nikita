package com.example.service;

import com.example.dto.UserDTO;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
        User entity = new User();
        UserDTO dto = new UserDTO();


        when(userRepository.findById(anyInt())).thenReturn(Optional.of(entity));
        when(userMapper.modelToDTO(entity)).thenReturn(dto);

        UserDTO byId = userService.getById(1);

        assertNotNull(byId);
        assertEquals(dto, byId);
    }

    @Test
    void testGetAll() {
        User entity = new User();
        UserDTO dto = new UserDTO();


        when(userRepository.findAll()).thenReturn(List.of(entity));
        when(userMapper.modelToDTO(entity)).thenReturn(dto);

        List<UserDTO> all = userService.getAll();

        assertFalse(all.isEmpty());
        assertEquals(1, all.size());
        assertEquals(dto, all.get(0));
    }

    @Test
    void testDeleteById() {
        Integer requestId = 1;
        when(userRepository.existsById(requestId)).thenReturn(true);

        userService.deleteById(requestId);

        verify(userRepository).existsById(requestId);
        verify(userRepository).deleteById(requestId);
    }

    @Test
    void testCreateUser() {
        User entity = new User();
        UserDTO dto = new UserDTO();


        when(userMapper.dtoToModel(dto)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.modelToDTO(entity)).thenReturn(dto);

        UserDTO result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals(dto, result);
    }


    @Test
    void testUpdateUser() {
        Integer targetUserId = 1;
        UserDTO userDTO = new UserDTO();

        User existingUser = new User();
        existingUser.setUserId(targetUserId);

        User currentUser = new User();
        currentUser.setUserId(2);

        lenient().when(userRepository.findById(targetUserId)).thenReturn(Optional.of(existingUser));

        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", "password");
        SecurityContextHolder.getContext().setAuthentication(auth);
        lenient().when(userRepository.findByUserUsername("testUser")).thenReturn(Optional.of(currentUser));

        assertThatThrownBy(() -> userService.updateUser(targetUserId, userDTO))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("You can only edit your own profile");

        verify(userRepository, never()).save(any());
    }
}

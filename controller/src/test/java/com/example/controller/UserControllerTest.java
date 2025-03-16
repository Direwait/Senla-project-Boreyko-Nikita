package com.example.controller;

import com.example.dto.UserDTO;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        UserDTO book1 = new UserDTO();
        UserDTO book2 = new UserDTO();
        List<UserDTO> books = Arrays.asList(book1, book2);

        when(userService.getAll()).thenReturn(books);

        ResponseEntity<List<UserDTO>> response = userController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).getAll();
    }

    @Test
    void testGetById() {
        UserDTO book = new UserDTO();
        when(userService.getById(1)).thenReturn(book);

        ResponseEntity<UserDTO> response = userController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(userService).getById(1);
    }

    @Test
    void testCreateUser() {
        UserDTO bookDTO = new UserDTO();
        when(userService.createUser(any(UserDTO.class))).thenReturn(bookDTO);

        ResponseEntity<UserDTO> response = userController.createUser(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(userService).createUser(bookDTO);
    }

    @Test
    void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        when(userService.updateUser(1, userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(1, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(userService).updateUser(1, userDTO);
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<Void> response = userController.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).deleteById(1);
    }
}
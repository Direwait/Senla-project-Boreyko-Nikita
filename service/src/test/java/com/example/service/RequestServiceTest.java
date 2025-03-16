package com.example.service;

import com.example.dto.RequestDTO;
import com.example.mapper.RequestMapper;
import com.example.model.Book;
import com.example.model.Request;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.RequestRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @InjectMocks
    RequestService requestService;
    @Mock
    RequestRepository requestRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    RequestMapper requestMapper;

    @Test
    void testGetById() {
        var entity = new Request();
        var dto = new RequestDTO();

        when(requestRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        when(requestMapper.modelToDTO(entity)).thenReturn(dto);

        var byId = requestService.getById(1);

        assertNotNull(byId);
        assertEquals(dto, byId);
    }

    @Test
    void testGetAll() {
        var entity = new Request();
        var dto = new RequestDTO();

        when(requestRepository.findAll()).thenReturn(List.of(entity));
        when(requestMapper.modelToDTO(entity)).thenReturn(dto);

        List<RequestDTO> all = requestService.getAll();

        Assertions.assertFalse(all.isEmpty());
        assertEquals(1, all.size());
    }

    @Test
    void testDeleteById() {
        Integer requestId = 1;
        when(requestRepository.existsById(requestId)).thenReturn(true);

        requestService.deleteById(requestId);

        verify(requestRepository).existsById(requestId);
        verify(requestRepository).deleteById(requestId);
    }

    @Test
    void testUpdateDto() {
        var request = new Request();
        request.setRequestId(1);

        var requestDTO = new RequestDTO();

        var updatedRequest = new Request();

        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        when(requestMapper.modelToDTO(any(Request.class))).thenReturn(requestDTO);
        when(requestRepository.save(any(Request.class))).thenReturn(updatedRequest);

        RequestDTO updatedRequestDTO = requestService.updateRequest(1, requestDTO);

        assertNotNull(updatedRequestDTO);

        verify(requestRepository).findById(1);
        verify(requestMapper).updateRequestFromDTO(requestDTO, request);
        verify(requestRepository).save(request);
        Mockito.verifyNoMoreInteractions(requestRepository, requestMapper);
    }

    @Test
    void createRequestTest() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setBookId(1);

        Book book = new Book();
        book.setBookId(1);

        User user = new User();
        user.setUserUsername("testUser");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(userRepository.findByUserUsername("testUser")).thenReturn(Optional.of(user));
        when(requestMapper.modelToDTO(any())).thenReturn(requestDTO);

        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", "password");
        SecurityContextHolder.getContext().setAuthentication(auth);

        RequestDTO result = requestService.createRequest(requestDTO);

        assertNotNull(result);
        verify(requestRepository).save(any(Request.class));
    }
}

package com.example.service;

import com.example.dto.RequestDTO;
import com.example.mapper.RequestMapper;
import com.example.model.Request;
import com.example.repository.RequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @InjectMocks
    RequestService requestService;
    @Mock
    RequestRepository requestRepository;
    @Mock
    RequestMapper requestMapper;

    @Test
    void testGetById() {
        var entity = new Request();
        var dto = new RequestDTO();

        Mockito.when(requestRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        Mockito.when(requestMapper.modelToDTO(entity)).thenReturn(dto);

        var byId = requestService.getById(1);

        Assertions.assertNotNull(byId);
        Assertions.assertEquals(dto, byId);
    }

    @Test
    void testGetAll() {
        var entity = new Request();
        var dto = new RequestDTO();

        Mockito.when(requestRepository.findAll()).thenReturn(List.of(entity));
        Mockito.when(requestMapper.modelToDTO(entity)).thenReturn(dto);

        List<RequestDTO> all = requestService.getAll();

        Assertions.assertFalse(all.isEmpty());
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(dto, all.get(0));
    }

    @Test
    void testDeleteById() {
        requestService.deleteById(Mockito.anyInt());

        Mockito.verify(requestRepository).deleteById(Mockito.anyInt());
    }

    @Test
    void testCreateDto() {
        var entity = new Request();
        var dto = new RequestDTO();

        Mockito.when(requestMapper.dtoToModel(dto)).thenReturn(entity);
        Mockito.when(requestRepository.save(entity)).thenReturn(entity);
        Mockito.when(requestMapper.modelToDTO(entity)).thenReturn(dto);

        var result = requestService.createRequest(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void testUpdateDto() {
        var request = new Request();
        request.setRequestId(1);

        var requestDTO = new RequestDTO();

        var updatedRequest = new Request();

        Mockito.when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        Mockito.when(requestMapper.modelToDTO(Mockito.any(Request.class))).thenReturn(requestDTO);
        Mockito.when(requestRepository.save(Mockito.any(Request.class))).thenReturn(updatedRequest);

        RequestDTO updatedRequestDTO = requestService.updateRequest(1, requestDTO);

        Assertions.assertNotNull(updatedRequestDTO);

        Mockito.verify(requestRepository).findById(1);
        Mockito.verify(requestMapper).updateRequestFromDTO(requestDTO, request);
        Mockito.verify(requestRepository).save(request);
        Mockito.verifyNoMoreInteractions(requestRepository, requestMapper);
    }
}

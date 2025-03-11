package com.example.service;


import com.example.dto.RequestDTO;
import com.example.mapper.RequestMapper;
import com.example.model.Request;
import com.example.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper rentalMapper;

    @Transactional
    public RequestDTO getById(Integer id) {
        Request request = requestRepository.findById(id).orElseThrow();
        return rentalMapper.modelToDTO(request);
    }

    public List<RequestDTO> getAll() {
        return requestRepository.findAll().stream()
                .map(rentalMapper::modelToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        requestRepository.deleteById(id);
    }

    @Transactional
    public RequestDTO createRequest(RequestDTO catalogDTO) {
        Request request = rentalMapper.dtoToModel(catalogDTO);
        Request save = requestRepository.save(request);
        return rentalMapper.modelToDTO(save);
    }

    @Transactional
    public RequestDTO updateRequest(Integer id, RequestDTO requestDTO) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запрос с ID " + id + " не найдена"));
        rentalMapper.updateRequestFromDTO(requestDTO, request);
        Request updatedRental = requestRepository.save(request);

        return rentalMapper.modelToDTO(updatedRental);
    }
}
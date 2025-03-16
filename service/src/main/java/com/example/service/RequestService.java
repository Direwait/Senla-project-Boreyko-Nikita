package com.example.service;


import com.example.dto.RequestDTO;
import com.example.mapper.RequestMapper;
import com.example.model.Book;
import com.example.model.Request;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.RequestRepository;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequestService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Transactional
    public RequestDTO getById(Integer id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Request with ID {} not found", id);
                    return new EntityNotFoundException("Request with ID " + id + " not found");
                });

        log.info("Successfully retrieved request ID: {}", id);
        return requestMapper.modelToDTO(request);
    }

    public List<RequestDTO> getAll() {
        List<RequestDTO> requestDTOS = requestRepository.findAll().stream()
                .map(requestMapper::modelToDTO)
                .collect(Collectors.toList());

        log.info("Found {} requests", requestDTOS.size());
        return requestDTOS;
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!requestRepository.existsById(id)) {
            log.error("Delete failed: Request with ID {} not found", id);
            throw new EntityNotFoundException("Request with ID " + id + " not found");
        }

        requestRepository.deleteById(id);
        log.info("Successfully deleted Request with ID: {}", id);
    }

    @Transactional
    public RequestDTO createRequest(RequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = userRepository.findByUserUsername(authentication.getName())
                .orElseThrow(() -> {
                    log.error("User not found in security context");
                    return new RuntimeException("User not found in security context");
                });

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> {
                    log.error("Book not found");
                    return new EntityNotFoundException("Book not found");
                });

        User user = userRepository.findByUserUsername(currentUser.getUserUsername())
                .orElseThrow(() -> {
                    log.error("User not found");
                    return new EntityNotFoundException("User not found");
                });

        Request saveRequest = Request.builder()
                .book(book)
                .user(user)
                .requestDate(new Date())
                .build();
        requestRepository.save(saveRequest);

        log.info("Request created successfully. ID: {}", saveRequest.getRequestId());
        return requestMapper.modelToDTO(saveRequest);
    }

    @Transactional
    public RequestDTO updateRequest(Integer id, RequestDTO requestDTO) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request with ID " + id + " not found"));
        requestMapper.updateRequestFromDTO(requestDTO, request);
        Request updatedRental = requestRepository.save(request);

        log.info("Request ID: {} successfully updated", id);
        return requestMapper.modelToDTO(updatedRental);
    }
}
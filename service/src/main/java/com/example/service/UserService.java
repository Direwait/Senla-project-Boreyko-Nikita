package com.example.service;

import com.example.dto.UserDTO;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", id);
                    return new RuntimeException("User not found");
                });

        log.info("Successfully retrieved user ID: {}", id);
        return userMapper.modelToDTO(user);
    }

    public List<UserDTO> getAll() {
        List<UserDTO> userDTOS = userRepository.findAll().stream()
                .map(userMapper::modelToDTO)
                .collect(Collectors.toList());

        log.info("Found {} users ", userDTOS.size());
        return userDTOS;
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            log.error("Delete failed: User with ID {} not found", id);
            throw new EntityNotFoundException("Request with ID " + id + " not found");
        }

        userRepository.deleteById(id);
        log.info("Successfully deleted User with ID: {}", id);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.dtoToModel(userDTO);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        User saveUser = userRepository.save(user);

        log.info("Request created successfully. ID: {}", saveUser.getUserId());
        return userMapper.modelToDTO(saveUser);
    }

    @Transactional
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        User currentUser = getCurrentUser();

        if (!id.equals(currentUser.getUserId())) {
            log.error("You can only edit your own profile");
            throw new AccessDeniedException("You can only edit your own profile");
        }
        userMapper.updateUserFromDTO(userDTO, user);
        user.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
        User updatedUser = userRepository.save(user);

        log.info("User ID: {} successfully updated", id);
        return userMapper.modelToDTO(updatedUser);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserUsername(authentication.getName())
                .orElseThrow(() -> {
                    log.error("User not found in security context");
                    return new RuntimeException("User not found in security context");
                });
    }

}

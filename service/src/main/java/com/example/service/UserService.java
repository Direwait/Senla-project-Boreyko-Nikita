package com.example.service;

import com.example.dto.UserDTO;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO getById(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        return userMapper.modelToDTO(user);
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::modelToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.dtoToModel(userDTO);

        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        User save = userRepository.save(user);
        return userMapper.modelToDTO(save);
    }

    @Transactional
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        User currentUser = getCurrentUser();

        if (!id.equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You can only edit your own profile");
        }

        userMapper.updateUserFromDTO(userDTO, user);
        user.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));

        User updatedUser = userRepository.save(user);
        return userMapper.modelToDTO(updatedUser);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found in security context"));
    }

}

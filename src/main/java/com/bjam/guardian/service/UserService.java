package com.bjam.guardian.service;

import com.bjam.guardian.dto.UserCreateDTO;
import com.bjam.guardian.dto.UserResponseDTO;
import com.bjam.guardian.exception.ResourceNotFoundException;
import com.bjam.guardian.model.User;
import com.bjam.guardian.repository.UserRepository;
import com.bjam.guardian.util.DTOMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        // converter DTO -> entidade
        User u = DTOMapper.toUserEntity(dto);
        // gerar hash
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        // salvar
        User saved = userRepo.save(u);
        return DTOMapper.toUserResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return DTOMapper.toUserResponse(u);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepo.findAll().stream()
                .map(DTOMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDTO update(Long id, UserCreateDTO dto) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        User saved = userRepo.save(u);
        return DTOMapper.toUserResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User findEntityById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
}

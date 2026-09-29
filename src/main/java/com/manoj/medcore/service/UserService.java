package com.manoj.medcore.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.LoginRequestDTO;
import com.manoj.medcore.dto.LoginResponseDTO;
import com.manoj.medcore.dto.UserRegisterDTO;
import com.manoj.medcore.dto.UserResponseDTO;
import com.manoj.medcore.model.User;
import com.manoj.medcore.repository.UserRepository;
import com.manoj.medcore.security.JwtService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDTO registerUser(UserRegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setUsername(savedUser.getUsername());
        responseDTO.setRole(savedUser.getRole());

        return responseDTO;
    }

    public Optional<LoginResponseDTO> loginUser(LoginRequestDTO dto) {
        return userRepository.findByUsername(dto.getUsername())
                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .map(user -> {
                    LoginResponseDTO response = new LoginResponseDTO();
                    response.setAccessToken(jwtService.generateToken(user.getUsername(), user.getRole()));
                    response.setTokenType("Bearer");
                    response.setExpiresInSeconds(jwtService.getExpirationSeconds());
                    response.setUsername(user.getUsername());
                    response.setRole(user.getRole());
                    return response;
                });
    }
}

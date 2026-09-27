package com.manoj.medcore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.medcore.dto.UserRegisterDTO;
import com.manoj.medcore.dto.UserResponseDTO;
import com.manoj.medcore.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(
            @Valid @RequestBody UserRegisterDTO dto) {

        UserResponseDTO response = userService.registerUser(dto);
        return ResponseEntity.status(201).body(response);
    }
}

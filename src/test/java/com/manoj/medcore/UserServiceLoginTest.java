package com.manoj.medcore;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.manoj.medcore.dto.LoginRequestDTO;
import com.manoj.medcore.dto.LoginResponseDTO;
import com.manoj.medcore.model.User;
import com.manoj.medcore.repository.UserRepository;
import com.manoj.medcore.security.JwtService;
import com.manoj.medcore.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceLoginTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void successfulLoginMatchesStoredPasswordAndUsesStoredRole() {
        User user = new User("admin", "$2a$stored-hash", "ADMIN");
        LoginRequestDTO request = loginRequest("admin", "admin123");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("admin123", "$2a$stored-hash")).thenReturn(true);
        when(jwtService.generateToken("admin", "ADMIN")).thenReturn("signed-token");
        when(jwtService.getExpirationSeconds()).thenReturn(1800L);

        Optional<LoginResponseDTO> result = userService.loginUser(request);

        assertTrue(result.isPresent());
        assertEquals("signed-token", result.get().getAccessToken());
        assertEquals("ADMIN", result.get().getRole());
        assertEquals(1800, result.get().getExpiresInSeconds());
        verify(passwordEncoder).matches("admin123", "$2a$stored-hash");
        verify(jwtService).generateToken("admin", "ADMIN");
    }

    @Test
    void wrongPasswordDoesNotIssueToken() {
        User user = new User("admin", "$2a$stored-hash", "ADMIN");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "$2a$stored-hash")).thenReturn(false);

        assertTrue(userService.loginUser(loginRequest("admin", "wrong")).isEmpty());
    }

    @Test
    void unknownUsernameDoesNotIssueToken() {
        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        assertTrue(userService.loginUser(loginRequest("missing", "admin123")).isEmpty());
    }

    private LoginRequestDTO loginRequest(String username, String password) {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }
}
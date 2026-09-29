package com.manoj.medcore;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.manoj.medcore.config.SecurityConfig;
import com.manoj.medcore.controller.UserController;
import com.manoj.medcore.dto.LoginRequestDTO;
import com.manoj.medcore.dto.LoginResponseDTO;
import com.manoj.medcore.dto.UserResponseDTO;
import com.manoj.medcore.security.JwtAuthenticationFilter;
import com.manoj.medcore.security.JwtService;
import com.manoj.medcore.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@WebMvcTest(UserController.class)
@Import({ SecurityConfig.class, JwtAuthenticationFilter.class, JwtService.class })
@TestPropertySource(properties = "jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=")
class JwtSecurityIntegrationTest {

    private static final String TEST_SECRET = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @Test
    void registrationRemainsPublicAndDoesNotReturnPassword() throws Exception {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(1L);
        response.setUsername("admin");
        response.setRole("ADMIN");
        when(userService.registerUser(any())).thenReturn(response);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"admin123\",\"role\":\"ADMIN\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void successfulLoginReturnsBearerTokenWithoutPassword() throws Exception {
        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(jwtService.generateToken("admin", "ADMIN"));
        response.setTokenType("Bearer");
        response.setExpiresInSeconds(1800);
        response.setUsername("admin");
        response.setRole("ADMIN");
        when(userService.loginUser(any(LoginRequestDTO.class))).thenReturn(Optional.of(response));

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresInSeconds").value(1800))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void invalidCredentialsReturnGenericUnauthorizedResponse() throws Exception {
        when(userService.loginUser(any(LoginRequestDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid username or password"));
    }

    @Test
    void protectedRouteRejectsMissingToken() throws Exception {
        mockMvc.perform(get("/users/protected-probe"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"));
        mockMvc.perform(get("/hospitals"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    void validTokenAuthenticatesProtectedRoute() throws Exception {
        String token = jwtService.generateToken("admin", "ADMIN");

        mockMvc.perform(get("/users/protected-probe")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void malformedAndExpiredTokensAreUnauthorized() throws Exception {
        String invalidToken = new JwtService(
                "YWJjZGVmMDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=")
                .generateToken("admin", "ADMIN");
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(TEST_SECRET));
        String expiredToken = Jwts.builder()
                .subject("admin")
                .claim("role", "ADMIN")
                .expiration(Date.from(Instant.now().minusSeconds(60)))
                .signWith(key)
                .compact();

        mockMvc.perform(get("/users/protected-probe")
                        .header("Authorization", "Bearer not-a-jwt"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/users/protected-probe")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/users/protected-probe")
                        .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized());
    }
}
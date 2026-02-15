package com.ahmad.book.application.port.service;

import com.ahmad.book.application.port.out.JwtTokenPort;
import com.ahmad.book.application.port.out.SecurityConfigPort;
import com.ahmad.book.application.service.AuthService;
import com.ahmad.book.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private SecurityConfigPort securityConfigPort;

    @Mock
    private JwtTokenPort jwtTokenPort;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User()
                .setUsername("admin")
                .setPassword("password")
                .setRole("ROLE_ADMIN");
    }

    @Test
    void login_ShouldReturnToken() {
        // Given
        when(securityConfigPort.getConfiguredUser()).thenReturn(user);
        when(jwtTokenPort.generateToken(user.getUsername(), user.getRole())).thenReturn("token");

        // When
        String token = authService.login(user.getUsername(), user.getPassword());

        // Then
        assertNotNull(token);
        assertEquals("token", token);

    }
}

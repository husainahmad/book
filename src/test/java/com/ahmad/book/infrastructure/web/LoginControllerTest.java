package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.port.out.JwtTokenPort;
import com.ahmad.book.application.service.AuthService;
import com.ahmad.book.domain.exception.UnAuthorizedRequestException;
import com.ahmad.book.infrastructure.web.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenPort jwtTokenPort;

    @Test
    void login_ShouldReturnToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin123");

        String token = "SAMPLE-TOKEN";
        Mockito.when(authService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(token);

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(token));

    }

    @Test
    void login_ShouldReturnBadRequest_WhenUsernameIsNull() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("admin123");

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_ShouldReturnBadRequest_WhenPasswordIsNull() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_ShouldReturnBadRequest_WhenUsernamePasswordNotValid() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("wrong-password");

        Mockito.when(authService.login(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new UnAuthorizedRequestException("exception.auth.username.password.notFound", null));

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}

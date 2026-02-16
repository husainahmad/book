package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.service.AuthService;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import com.ahmad.book.infrastructure.web.dto.LoginRequest;
import com.ahmad.book.infrastructure.web.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
@Tag(name = "Login", description = "API for user authentication and obtaining JWT tokens.")
public class LoginController {

    private final AuthService authService;

    @PostMapping
    @Operation(summary = "Authenticate user and obtain JWT token", description = "Authenticates the user with the provided username and password, and returns a JWT token if the credentials are valid.")
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(authService.login(loginRequest.getUsername(), loginRequest.getPassword()));

        return new ResponseEntity<>(CommonResponse.builder()
                .data(loginResponse)
                .build(), HttpStatus.OK);
    }

}

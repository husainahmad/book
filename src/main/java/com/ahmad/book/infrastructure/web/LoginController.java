package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.service.LoginService;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import com.ahmad.book.infrastructure.web.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(CommonResponse.builder()
                .data(loginService.login(loginRequest.getUsername(), loginRequest.getPassword()))
                .build(), HttpStatus.OK);
    }

}

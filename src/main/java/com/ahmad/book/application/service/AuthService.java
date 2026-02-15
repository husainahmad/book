package com.ahmad.book.application.service;

import com.ahmad.book.application.port.in.AuthServicePort;
import com.ahmad.book.application.port.out.JwtTokenPort;
import com.ahmad.book.application.port.out.SecurityConfigPort;
import com.ahmad.book.domain.exception.UnAuthorizedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServicePort {

    private final SecurityConfigPort securityConfigPort;
    private final JwtTokenPort jwtTokenPort;

    @Override
    public String login(String username, String password) {
        if (securityConfigPort.getConfiguredUser().getUsername().equals(username) &&
                securityConfigPort.getConfiguredUser().getPassword().equals(password)) {
            return jwtTokenPort.generateToken(username, securityConfigPort.getConfiguredUser().getRole());
        }
        throw new UnAuthorizedRequestException("exception.auth.username.password.notFound", null);
    }
}

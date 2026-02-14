package com.ahmad.book.infrastructure.security;

import com.ahmad.book.application.port.out.SecurityConfigPort;
import com.ahmad.book.domain.User;
import com.ahmad.book.infrastructure.config.LoanSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityConfigAdapter implements SecurityConfigPort {

    private final LoanSecurityProperties properties;

    @Override
    public User getConfiguredUser() {
        return new User().setUsername(properties.getUser())
                .setPassword(properties.getPass())
                .setRole(properties.getRole());
    }
}

package com.ahmad.book.infrastructure.web.dto;

import com.ahmad.book.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "book.username.required")
    private String username;
    @NotBlank(message = "book.password.required")
    private String password;

    public User toUser() {
        return new User()
                .setUsername(this.username)
                .setPassword(this.password);
    }
}

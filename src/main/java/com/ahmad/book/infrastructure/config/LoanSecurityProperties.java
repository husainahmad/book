package com.ahmad.book.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "book.loan")
@Data
public class LoanSecurityProperties {
    private String user;
    private String pass;
    private String role;
}

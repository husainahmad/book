package com.ahmad.book.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "book.loan.config")
@Data
public class LoanConfigProperties {
    private long maxActive;
    private long dueDate;
}

package com.ahmad.book.application.port.out;


public interface JwtTokenPort {
    String generateToken(String user, String role);
    boolean validate(String token, String username);
    boolean isTokenExpired(String token);
    String extractUsername(String token);
    String extractRole(String token);
}

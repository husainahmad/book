package com.ahmad.book.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StateException extends RuntimeException {
    private final String message;
    private final transient Object[] args;
}

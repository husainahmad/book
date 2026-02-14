package com.ahmad.book.infrastructure.web.exception;

import com.ahmad.book.domain.exception.AlreadyExistException;
import com.ahmad.book.domain.exception.NotFoundException;
import com.ahmad.book.domain.exception.StateException;
import com.ahmad.book.domain.exception.UnAuthorizedRequestException;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(UnAuthorizedRequestException.class)
    public ResponseEntity<CommonResponse> handleUnAuthorizedException(UnAuthorizedRequestException e, Locale locale) {
        String messageName = e.getMessage();
        Object[] args = e.getArgs();
        String message = messageSource.getMessage(messageName, args, locale);

        CommonResponse restAPIResponse = CommonResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .error(message)
                .data(null)
                .build();

        log.warn("Unauthorized access: {}", message);
        return new ResponseEntity<>(restAPIResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(
            Exception e,
            Locale locale) {

        String message = messageSource.getMessage("internal.server.error", null, "Internal server error", locale);

        CommonResponse restAPIResponse = CommonResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .error(message)
                .data(null)
                .build();

        log.error("Internal Server Error", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(restAPIResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse> handleNotFoundRequestException(NotFoundException e, Locale locale) {
        String messageName = e.getMessage();
        Object[] args = e.getArgs();
        String message = messageSource.getMessage(messageName, args, locale);

        CommonResponse restAPIResponse = CommonResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .error(message)
                .data(null)
                .build();

        log.warn("Not Found : {}", message);
        return new ResponseEntity<>(restAPIResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<CommonResponse> handleAlreadyExistException(AlreadyExistException e, Locale locale) {
        String messageName = e.getMessage();
        Object[] args = e.getArgs();
        String message = messageSource.getMessage(messageName, args, locale);

        CommonResponse restAPIResponse = CommonResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .error(message)
                .data(null)
                .build();

        log.warn("Already Exist : {}", message);
        return new ResponseEntity<>(restAPIResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StateException.class)
    public ResponseEntity<CommonResponse> handleStateException(StateException e, Locale locale) {
        String messageName = e.getMessage();
        Object[] args = e.getArgs();
        String message = messageSource.getMessage(messageName, args, locale);

        CommonResponse restAPIResponse = CommonResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .error(message)
                .data(null)
                .build();

        log.warn("Not Allowed : {}", message);
        return new ResponseEntity<>(restAPIResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            Locale locale) {

        String errorMessage = "Validation error";

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            FieldError error = fieldErrors.get(0);

            errorMessage = messageSource.getMessage(
                    Objects.requireNonNull(error.getDefaultMessage()),
                    null,
                    error.getDefaultMessage(),
                    locale
            );
        }

        CommonResponse response = CommonResponse.builder()
                .timeStamp(System.currentTimeMillis())
                .error(errorMessage)
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }


}

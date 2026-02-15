package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.service.BookService;
import com.ahmad.book.domain.Book;
import com.ahmad.book.infrastructure.web.dto.BookRequest;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API for managing books in the library system.")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create a new book", description = "Creates a new book in the library system with the provided details.")
    public ResponseEntity<CommonResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        Book book = bookRequest.toBook();
        return new ResponseEntity<>(CommonResponse.builder()
                .data(bookService.createBook(book)).build(), HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "Get all books", description = "Retrieves a list of all books available in the library system.")
    public ResponseEntity<CommonResponse> getAll() {
        return new ResponseEntity<>(CommonResponse.builder()
                .data(bookService.getAllBooks()).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves the details of a specific book by its ID.")
    public ResponseEntity<CommonResponse> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(CommonResponse.builder()
                .data(bookService.getBookById(id))
                .build(), HttpStatus.OK);
    }
}

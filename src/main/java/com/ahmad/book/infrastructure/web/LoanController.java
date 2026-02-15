package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.service.LoanService;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import com.ahmad.book.infrastructure.web.dto.LoanRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loans")
@Tag(name = "Loans", description = "API for managing book loans in the library system.")
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @Operation(summary = "Loan a book", description = "Loans a book to a member based on the provided book ID and member ID.")
    public ResponseEntity<CommonResponse> loanBook(@Valid @RequestBody LoanRequest loanRequest) {
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(loanService.loanBook(loanRequest.getBookId(), loanRequest.getMemberId()))
                .build());
    }

    @GetMapping("")
    @Operation(summary = "Get all loans", description = "Retrieves a list of all active loans in the library system.")
    public ResponseEntity<CommonResponse> getAll() {
        return ResponseEntity.ok(CommonResponse.builder()
                .data(loanService.getAllLoans())
                .build());
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return a book", description = "Retrieves the details of a specific loan by its ID.")
    public ResponseEntity<CommonResponse> returnBook(@PathVariable Long id, @Valid @RequestBody LoanRequest loanRequest) {
        return ResponseEntity.ok(CommonResponse.builder()
                .data(loanService.returnBook(id, loanRequest.getBookId(), loanRequest.getMemberId()))
                .build());
    }
}

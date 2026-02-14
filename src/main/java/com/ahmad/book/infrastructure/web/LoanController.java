package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.service.LoanService;
import com.ahmad.book.infrastructure.web.dto.CommonResponse;
import com.ahmad.book.infrastructure.web.dto.LoanRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<CommonResponse> loanBook(@Valid @RequestBody LoanRequest loanRequest) {
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(loanService.loanBook(loanRequest.getBookId(), loanRequest.getMemberId()))
                .build());
    }

    @GetMapping("")
    public ResponseEntity<CommonResponse> getAll() {
        return ResponseEntity.ok(CommonResponse.builder()
                .data(loanService.getAllLoans())
                .build());
    }
}

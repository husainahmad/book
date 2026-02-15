package com.ahmad.book.application.port.in;

import com.ahmad.book.domain.Loan;

import java.util.List;

public interface LoanServicePort {
    Loan loanBook(Long bookId, Long memberId);
    Loan returnBook(Long id, Long bookId, Long memberId);
    List<Loan> getAllLoans();
}

package com.ahmad.book.application.port.in;

import com.ahmad.book.domain.Loan;

import java.util.List;

public interface LoanServicePort {
    Loan loanBook(long bookId, long memberId);
    void returnBook(long bookId, long memberId);
    List<Loan> getAllLoans();
}

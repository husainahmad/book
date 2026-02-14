package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Loan;

public interface LoanRepositoryPort {
    void save(Loan loan);
    Loan findById(Long id);
    void update(Loan loan);
}

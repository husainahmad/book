package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Loan;

import java.util.List;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    Loan findById(Long id);
    List<Loan> findAll();
    List<Loan> findActiveByMemberId(Long memberId);
    List<Loan> findOverdueByMemberId(Long memberId);
    void update(Loan loan);
}

package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Loan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    Optional<Loan> findByIdBookIdMemberId(Long id, Long bookId, Long memberId);
    List<Loan> findAll();
    List<Loan> findActiveByMemberId(Long memberId);
    List<Loan> findOverdueByMemberId(Long memberId, LocalDateTime now);
    void update(Loan loan);
    void updateReturnedAt(Long id, LocalDateTime returnedAt);

}

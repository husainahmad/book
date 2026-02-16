package com.ahmad.book.infrastructure.persistence.mybatis.repository;

import com.ahmad.book.application.port.out.LoanRepositoryPort;
import com.ahmad.book.domain.Loan;
import com.ahmad.book.infrastructure.persistence.mybatis.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoanRepositoryAdapter implements LoanRepositoryPort {

    private final LoanMapper loanMapper;

    @Override
    public Loan save(Loan loan) {
        loanMapper.insert(loan);
        return loan;
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return Optional.ofNullable(loanMapper.selectById(id));
    }

    @Override
    public Optional<Loan> findByIdBookIdMemberId(Long id, Long bookId, Long memberId) {
        return Optional.ofNullable(loanMapper.selectByIdBookIdMemberId(id, bookId, memberId));
    }

    @Override
    public List<Loan> findAll() {
        return loanMapper.selectAll();
    }

    @Override
    public List<Loan> findActiveByMemberId(Long memberId) {
        return loanMapper.selectActiveByMemberId(memberId);
    }

    @Override
    public List<Loan> findOverdueByMemberId(Long memberId, LocalDateTime now) {
        return loanMapper.selectOverdueByMemberId(memberId, now);
    }

    @Override
    public void update(Loan loan) {
        loanMapper.update(loan);
    }

    @Override
    public void updateReturnedAt(Long id, LocalDateTime returnedAt) {
        loanMapper.updateReturnedAt(id, returnedAt);
    }
}

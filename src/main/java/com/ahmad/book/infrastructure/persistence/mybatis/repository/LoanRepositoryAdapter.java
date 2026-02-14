package com.ahmad.book.infrastructure.persistence.mybatis.repository;

import com.ahmad.book.application.port.out.LoanRepositoryPort;
import com.ahmad.book.domain.Loan;
import com.ahmad.book.infrastructure.persistence.mybatis.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Loan findById(Long id) {
        return loanMapper.selectById(id);
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
    public List<Loan> findOverdueByMemberId(Long memberId) {
        return loanMapper.selectOverdueByMemberId(memberId);
    }

    @Override
    public void update(Loan loan) {
        loanMapper.update(loan);
    }
}

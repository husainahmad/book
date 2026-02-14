package com.ahmad.book.infrastructure.persistence.mybatis.repository;

import com.ahmad.book.application.port.out.LoanRepositoryPort;
import com.ahmad.book.domain.Loan;
import com.ahmad.book.infrastructure.persistence.mybatis.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoanRepositoryAdapter implements LoanRepositoryPort {

    private final LoanMapper loanMapper;

    @Override
    public void save(Loan loan) {
        loanMapper.insert(loan);
    }

    @Override
    public Loan findById(Long id) {
        return loanMapper.selectById(id);
    }

    @Override
    public void update(Loan loan) {
        loanMapper.update(loan);
    }
}

package com.ahmad.book.application.service;

import com.ahmad.book.application.port.in.LoanServicePort;
import com.ahmad.book.application.port.in.MemberServicePort;
import com.ahmad.book.application.port.out.BookRepositoryPort;
import com.ahmad.book.application.port.out.LoanRepositoryPort;
import com.ahmad.book.domain.Book;
import com.ahmad.book.domain.Loan;
import com.ahmad.book.domain.Member;
import com.ahmad.book.domain.exception.NotFoundException;
import com.ahmad.book.domain.exception.StateException;
import com.ahmad.book.infrastructure.config.LoanConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService implements LoanServicePort {

    private final LoanRepositoryPort loanRepositoryPort;
    private final BookRepositoryPort bookRepositoryPort;
    private final MemberServicePort memberServicePort;

    private final LoanConfigProperties loanConfigProperties;

    @Override
    public Loan loanBook(long bookId, long memberId) {
        Book book = bookRepositoryPort.findById(bookId);
        if (book == null) {
            throw new NotFoundException("exception.book.notFound", null);
        }

        Member member = memberServicePort.getMemberById(memberId);
        if (member == null) {
            throw new NotFoundException("exception.member.notFound", null);
        }

        if (loanRepositoryPort.findActiveByMemberId(memberId).size() > loanConfigProperties.getMaxActive()) {
            throw new StateException("exception.loan.maxLoansReached", null);
        }

        if (!loanRepositoryPort.findOverdueByMemberId(memberId).isEmpty()) {
            throw new StateException("exception.loan.overdueExists", null);
        }

        Loan loan = new Loan()
                .setBookId(bookId)
                .setMemberId(memberId)
                .setBorrowedAt(LocalDateTime.now())
                .setDueDate(LocalDateTime.now().plusDays(loanConfigProperties.getDueDate()));

        return loanRepositoryPort.save(loan);
    }

    @Override
    public void returnBook(long bookId, long memberId) {

    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepositoryPort.findAll();
    }
}

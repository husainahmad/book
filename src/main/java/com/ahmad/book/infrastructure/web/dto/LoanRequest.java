package com.ahmad.book.infrastructure.web.dto;

import com.ahmad.book.domain.Loan;
import lombok.Data;

import java.util.Date;

@Data
public class LoanRequest {
    private Long bookId;
    private Long memberId;
    private Date borrowedAt;
    private Date dueDate;

    Loan toLoan() {
        return new Loan()
                .setBookId(bookId)
                .setMemberId(memberId)
                .setBorrowedAt(borrowedAt)
                .setDueDate(dueDate);
    }
}

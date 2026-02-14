package com.ahmad.book.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanRequest {

    @NotNull(message = "book.loan.bookId.required")
    @JsonProperty("book_id")
    private Long bookId;
    @NotNull(message = "book.loan.memberId.required")
    @JsonProperty("member_id")
    private Long memberId;
}

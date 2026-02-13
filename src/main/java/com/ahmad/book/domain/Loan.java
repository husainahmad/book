package com.ahmad.book.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan {
    private Long id;
    private Long bookId;
    private Long memberId;
    private Date borrowedAt;
    private Date dueDate;
    private Date returnedAt;
    private Date createdAt;
    private Date updatedAt;
}

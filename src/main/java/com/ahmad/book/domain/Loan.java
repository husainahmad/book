package com.ahmad.book.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan {
    private Long id;
    @JsonProperty("book_id")
    private Long bookId;
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("borrowed_at")
    private LocalDateTime borrowedAt;
    @JsonProperty("due_date")
    private LocalDateTime dueDate;
    @JsonProperty("returned_at")
    private LocalDateTime returnedAt;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}

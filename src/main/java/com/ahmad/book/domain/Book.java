package com.ahmad.book.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    @JsonProperty("total_copies")
    private int totalCopies;
    @JsonProperty("available_copies")
    private int availableCopies;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}

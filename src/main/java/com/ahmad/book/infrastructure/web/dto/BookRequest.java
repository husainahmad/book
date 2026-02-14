package com.ahmad.book.infrastructure.web.dto;

import com.ahmad.book.domain.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank(message = "book.title.required")
    private String title;
    @NotBlank(message = "book.author.required")
    private String author;
    @NotBlank(message = "book.isbn.required")
    private String isbn;
    @JsonProperty("total_copies")
    private int totalCopies;
    @JsonProperty("available_copies")
    private int availableCopies;

    public Book toBook() {
        return new Book()
                .setAuthor(this.author)
                .setTitle(this.title)
                .setIsbn(this.isbn)
                .setAvailableCopies(this.availableCopies)
                .setTotalCopies(this.totalCopies);
    }
}

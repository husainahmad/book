package com.ahmad.book.infrastructure.web.dto;

import com.ahmad.book.domain.Book;
import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
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

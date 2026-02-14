package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Book;

import java.util.List;

public interface BookRepositoryPort {
    Book save(Book book);
    Book findById(Long id);
    Book findByIsbn(String isbn);
    List<Book> findAll();
    void update(Book book);
}

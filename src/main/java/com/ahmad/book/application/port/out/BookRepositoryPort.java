package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {
    Book save(Book book);
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    void update(Book book);
}

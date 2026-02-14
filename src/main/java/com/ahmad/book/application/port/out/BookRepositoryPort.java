package com.ahmad.book.application.port.out;

import com.ahmad.book.domain.Book;

public interface BookRepositoryPort {
    void save(Book book);
    Book findById(Long id);
    void update(Book book);
}

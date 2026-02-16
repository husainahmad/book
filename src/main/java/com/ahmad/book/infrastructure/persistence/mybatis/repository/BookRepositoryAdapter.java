package com.ahmad.book.infrastructure.persistence.mybatis.repository;

import com.ahmad.book.application.port.out.BookRepositoryPort;
import com.ahmad.book.domain.Book;
import com.ahmad.book.infrastructure.persistence.mybatis.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryAdapter implements BookRepositoryPort {
    private final BookMapper bookMapper;

    @Override
    public Book save(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookMapper.selectById(id));
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(bookMapper.selectByIsbn(isbn));
    }

    @Override
    public List<Book> findAll() {
        return bookMapper.selectAll();
    }

    @Override
    public void update(Book book) {
        bookMapper.update(book);
    }
}

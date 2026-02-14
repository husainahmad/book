package com.ahmad.book.infrastructure.persistence.mybatis.repository;

import com.ahmad.book.application.port.out.BookRepositoryPort;
import com.ahmad.book.domain.Book;
import com.ahmad.book.infrastructure.persistence.mybatis.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Book findById(Long id) {
        return bookMapper.selectById(id);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookMapper.selectByIsbn(isbn);
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

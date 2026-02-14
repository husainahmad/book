package com.ahmad.book.application.service;

import com.ahmad.book.application.port.in.BookServicePort;
import com.ahmad.book.application.port.out.BookRepositoryPort;
import com.ahmad.book.domain.Book;
import com.ahmad.book.domain.exception.AlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements BookServicePort {

    private final BookRepositoryPort bookRepositoryPort;

    @Override
    public Book createBook(Book book) {
        if (bookRepositoryPort.findByIsbn(book.getIsbn()) != null) {
            throw new AlreadyExistException("exception.book.isbn.alreadyExists", null);
        }
        return bookRepositoryPort.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepositoryPort.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepositoryPort.findAll();
    }
}

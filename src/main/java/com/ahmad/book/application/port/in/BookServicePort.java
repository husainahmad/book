package com.ahmad.book.application.port.in;

import com.ahmad.book.domain.Book;

import java.util.List;

public interface BookServicePort {
    Book createBook(Book book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
}

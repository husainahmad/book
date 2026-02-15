package com.ahmad.book.application.port.service;

import com.ahmad.book.application.port.out.BookRepositoryPort;
import com.ahmad.book.application.service.BookService;
import com.ahmad.book.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepositoryPort bookRepositoryPort;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book()
                .setId(1L)
                .setTitle("Test Book")
                .setAuthor("Test Author")
                .setIsbn("1234567890")
                .setTotalCopies(10)
                .setAvailableCopies(10);
    }

    @Test
    void createBook_ShouldReturnCreatedBook() {
        // Given
        when(bookRepositoryPort.findByIsbn(book.getIsbn())).thenReturn(null);
        when(bookRepositoryPort.save(book)).thenReturn(book);

        // When
        Book createdBook = bookService.createBook(book);

        // Then
        assertNotNull(createdBook);
        assertEquals(book.getId(), createdBook.getId());
        assertEquals(book.getTitle(), createdBook.getTitle());
        assertEquals(book.getAuthor(), createdBook.getAuthor());
        assertEquals(book.getIsbn(), createdBook.getIsbn());
        assertEquals(book.getTotalCopies(), createdBook.getTotalCopies());
        assertEquals(book.getAvailableCopies(), createdBook.getAvailableCopies());

        verify(bookRepositoryPort).findByIsbn(book.getIsbn());
        verify(bookRepositoryPort).save(book);
    }

    @Test
    void getBookById_ShouldReturnBook() {
        // Given
        when(bookRepositoryPort.findById(book.getId())).thenReturn(book);

        // When
        Book foundBook = bookService.getBookById(book.getId());

        // Then
        assertNotNull(foundBook);
        assertEquals(book.getId(), foundBook.getId());
        assertEquals(book.getTitle(), foundBook.getTitle());
        assertEquals(book.getAuthor(), foundBook.getAuthor());
        assertEquals(book.getIsbn(), foundBook.getIsbn());
        assertEquals(book.getTotalCopies(), foundBook.getTotalCopies());
        assertEquals(book.getAvailableCopies(), foundBook.getAvailableCopies());

        verify(bookRepositoryPort, times(2)).findById(book.getId());
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        // Given
        when(bookRepositoryPort.findAll()).thenReturn(List.of(book));

        // When
        List<Book> books = bookService.getAllBooks();

        // Then
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getId(), books.get(0).getId());
        assertEquals(book.getTitle(), books.get(0).getTitle());
        assertEquals(book.getAuthor(), books.get(0).getAuthor());
        assertEquals(book.getIsbn(), books.get(0).getIsbn());
        assertEquals(book.getTotalCopies(), books.get(0).getTotalCopies());
        assertEquals(book.getAvailableCopies(), books.get(0).getAvailableCopies());

        verify(bookRepositoryPort).findAll();
    }
}

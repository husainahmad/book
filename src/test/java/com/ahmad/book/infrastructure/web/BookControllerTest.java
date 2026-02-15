package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.port.out.JwtTokenPort;
import com.ahmad.book.application.service.BookService;
import com.ahmad.book.domain.Book;
import com.ahmad.book.domain.exception.AlreadyExistException;
import com.ahmad.book.domain.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenPort jwtTokenPort;

    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        Book book = new Book()
                .setId(1L)
                .setTitle("Test Book")
                .setAuthor("Test Author")
                .setIsbn("1234567890")
                .setTotalCopies(10)
                .setAvailableCopies(10);

        Mockito.when(bookService.createBook(Mockito.any(Book.class))).thenReturn(book);
        mockMvc.perform(post("/api/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(book.getId()))
                .andExpect(jsonPath("$.data.title").value(book.getTitle()))
                .andExpect(jsonPath("$.data.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.data.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.data.total_copies").value(book.getTotalCopies()))
                .andExpect(jsonPath("$.data.available_copies").value(book.getAvailableCopies()));
    }

    @Test
    void createBook_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        Book book = new Book()
                .setTitle("") // Invalid title
                .setAuthor("Test Author")
                .setIsbn("1234567890")
                .setTotalCopies(10)
                .setAvailableCopies(10);

        mockMvc.perform(post("/api/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createBook_ShouldReturnBadRequest_WhenIsbnAlreadyExists() throws Exception {
        Book book = new Book()
                .setTitle("Test Book")
                .setAuthor("Test Author")
                .setIsbn("1234567890")
                .setTotalCopies(10)
                .setAvailableCopies(10);

        Mockito.when(bookService.createBook(Mockito.any(Book.class)))
                .thenThrow(new AlreadyExistException("exception.book.isbn.alreadyExists", null));

        mockMvc.perform(post("/api/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Book with ISBN already exists!"));
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        Book book1 = new Book()
                .setId(1L)
                .setTitle("Test Book 1")
                .setAuthor("Test Author 1")
                .setIsbn("1234567890")
                .setTotalCopies(10)
                .setAvailableCopies(10);

        Book book2 = new Book()
                .setId(2L)
                .setTitle("Test Book 2")
                .setAuthor("Test Author 2")
                .setIsbn("0987654321")
                .setTotalCopies(5)
                .setAvailableCopies(5);

        Mockito.when(bookService.getAllBooks()).thenReturn(java.util.List.of(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(book1.getId()))
                .andExpect(jsonPath("$.data[0].title").value(book1.getTitle()))
                .andExpect(jsonPath("$.data[0].author").value(book1.getAuthor()))
                .andExpect(jsonPath("$.data[0].isbn").value(book1.getIsbn()))
                .andExpect(jsonPath("$.data[0].total_copies").value(book1.getTotalCopies()))
                .andExpect(jsonPath("$.data[0].available_copies").value(book1.getAvailableCopies()))
                .andExpect(jsonPath("$.data[1].id").value(book2.getId()))
                .andExpect(jsonPath("$.data[1].title").value(book2.getTitle()))
                .andExpect(jsonPath("$.data[1].author").value(book2.getAuthor()))
                .andExpect(jsonPath("$.data[1].isbn").value(book2.getIsbn()))
                .andExpect(jsonPath("$.data[1].total_copies").value(book2.getTotalCopies()))
                .andExpect(jsonPath("$.data[1].available_copies").value(book2.getAvailableCopies()));
    }

    @Test
    void getBookById_ShouldReturnBook() throws Exception {
        Book book = new Book()
                .setId(1L)
                .setTitle("Test Book")
                .setAuthor("Test Author")
                .setIsbn("1234567890")
                .setTotalCopies(10)
                .setAvailableCopies(10);

        Mockito.when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(book.getId()))
                .andExpect(jsonPath("$.data.title").value(book.getTitle()))
                .andExpect(jsonPath("$.data.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.data.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.data.total_copies").value(book.getTotalCopies()))
                .andExpect(jsonPath("$.data.available_copies").value(book.getAvailableCopies()));
    }

    @Test
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() throws Exception {
        Mockito.when(bookService.getBookById(1L)).thenThrow(new NotFoundException("exception.book.notFound", null));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book not found!"));
    }
}

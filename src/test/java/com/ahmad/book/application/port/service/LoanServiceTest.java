package com.ahmad.book.application.port.service;

import com.ahmad.book.application.port.out.BookRepositoryPort;
import com.ahmad.book.application.port.out.LoanRepositoryPort;
import com.ahmad.book.application.port.out.MemberRepositoryPort;
import com.ahmad.book.application.service.LoanService;
import com.ahmad.book.domain.Book;
import com.ahmad.book.domain.Loan;
import com.ahmad.book.domain.Member;
import com.ahmad.book.domain.exception.NotFoundException;
import com.ahmad.book.domain.exception.StateException;
import com.ahmad.book.infrastructure.config.LoanConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepositoryPort loanRepositoryPort;

    @Mock
    private BookRepositoryPort bookRepositoryPort;

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    @InjectMocks
    private LoanService loanService;

    private Book book;
    private Member member;
    private Loan loan;

    @BeforeEach
    void setUp() {
        book = new Book()
                .setId(1L)
                .setTitle("Test Book")
                .setAuthor("Test Author")
                .setIsbn("1234567890")
                .setTotalCopies(10)
                .setAvailableCopies(10);

        member = new Member()
                .setId(1L)
                .setName("Jon Doe")
                .setEmail("jon.doe@book.lib");

        loan = new Loan()
                .setId(1L)
                .setBookId(book.getId())
                .setMemberId(member.getId());

        LoanConfigProperties config = new LoanConfigProperties();
        config.setMaxActive(5L);
        config.setDueDate(2L);

        loanService = new LoanService(
                loanRepositoryPort,
                bookRepositoryPort,
                memberRepositoryPort,
                config
        );
    }

    @Test
    void loanBook_ShouldThrowNotFoundException_WhenBookNotFound() {
        // Given
        when(bookRepositoryPort.findById(book.getId())).thenReturn(null);

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                loanService.loanBook(book.getId(), member.getId())
        );

        assertEquals("exception.book.notFound", exception.getMessage());
    }

    @Test
    void loanBook_ShouldThrowNotFoundException_WhenMemberNotFound() {
        // Given
        when(bookRepositoryPort.findById(book.getId())).thenReturn(book);
        when(memberRepositoryPort.findById(member.getId())).thenReturn(null);

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                loanService.loanBook(book.getId(), member.getId())
        );
        assertEquals("exception.member.notFound", exception.getMessage());
    }

    @Test
    void loanBook_ShouldThrowMaxLoansReachedException_WhenMemberReachMaxActive() {
        // Given
        when(bookRepositoryPort.findById(book.getId())).thenReturn(book);
        when(memberRepositoryPort.findById(member.getId())).thenReturn(member);

        List<Loan> activeLoans = List.of(
                new Loan(), new Loan(), new Loan(), new Loan(), new Loan(), new Loan()
        );

        when(loanRepositoryPort.findActiveByMemberId(eq(member.getId()))).thenReturn(activeLoans);

        StateException exception = assertThrows(StateException.class, () ->
                loanService.loanBook(book.getId(), member.getId())
        );

        assertEquals("exception.loan.maxLoansReached", exception.getMessage());

    }

    @Test
    void loanBook_ShouldThrowOverdueExistsException_WhenMemberHasOverdue() {
        // Given
        when(bookRepositoryPort.findById(book.getId())).thenReturn(book);
        when(memberRepositoryPort.findById(member.getId())).thenReturn(member);
        when(loanRepositoryPort.findActiveByMemberId(eq(member.getId()))).thenReturn(List.of(loan));
        when(loanRepositoryPort.findOverdueByMemberId(eq(member.getId()), any(LocalDateTime.class))).thenReturn(List.of(loan));

        // When & Then
        StateException exception = assertThrows(StateException.class, () ->
                loanService.loanBook(book.getId(), member.getId())
        );

        assertEquals("exception.loan.overdueExists", exception.getMessage());
    }


    @Test
    void loanBook_ShouldReturnLoan() {
        // Given
        when(bookRepositoryPort.findById(book.getId())).thenReturn(book);
        when(memberRepositoryPort.findById(member.getId())).thenReturn(member);
        when(loanRepositoryPort.findActiveByMemberId(eq(member.getId()))).thenReturn(List.of());
        when(loanRepositoryPort.findOverdueByMemberId(eq(member.getId()), any(LocalDateTime.class))).thenReturn(List.of());
        when(loanRepositoryPort.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Loan createdLoan = loanService.loanBook(book.getId(), member.getId());

        // Then
        assertNotNull(createdLoan);
        assertEquals(loan.getBookId(), createdLoan.getBookId());
        assertEquals(loan.getMemberId(), createdLoan.getMemberId());
    }

}

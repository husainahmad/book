package com.ahmad.book.infrastructure.web;

import com.ahmad.book.application.port.out.JwtTokenPort;
import com.ahmad.book.application.service.LoanService;
import com.ahmad.book.domain.Loan;
import com.ahmad.book.domain.exception.NotFoundException;
import com.ahmad.book.domain.exception.StateException;
import com.ahmad.book.infrastructure.web.dto.LoanRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenPort jwtTokenPort;

    @Test
    void loanBook_ShouldReturnLoanedBook() throws Exception {

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(1L);
        loanRequest.setMemberId(1L);

        Loan loan = new Loan()
                .setId(1L)
                .setBookId(loanRequest.getBookId())
                .setMemberId(loanRequest.getMemberId());

        Mockito.when(loanService.loanBook(Mockito.anyLong(), Mockito.anyLong())).thenReturn(loan);

        mockMvc.perform(post("/api/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(loan.getId()));

    }

    @Test
    void loanBook_ShouldReturnBadRequest_WhenBookIdIsNull() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setMemberId(1L);

        mockMvc.perform(post("/api/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Book ID is required."));
    }

    @Test
    void loanBook_ShouldReturnBadRequest_WhenMemberIdIsNull() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(1L);

        mockMvc.perform(post("/api/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Member ID is required."));
    }

    @Test
    void loanBook_ShouldReturnBadRequest_WhenBookIsNotAvailable() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(1L);
        loanRequest.setMemberId(1L);

        Mockito.when(loanService.loanBook(Mockito.anyLong(), Mockito.anyLong()))
                .thenThrow(new NotFoundException("exception.book.notFound", null));

        mockMvc.perform(post("/api/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Book is not available for loan."));
    }

    @Test
    void loanBook_ShouldReturnBadRequest_WhenMemberIsNotFound() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(1L);
        loanRequest.setMemberId(1L);

        Mockito.when(loanService.loanBook(Mockito.anyLong(), Mockito.anyLong()))
                .thenThrow(new NotFoundException("exception.member.notFound", null));

        mockMvc.perform(post("/api/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Member not found!"));
    }

    @Test
    void loanBook_ShouldReturnBadRequest_WhenMaxActiveLoansReached() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(1L);
        loanRequest.setMemberId(1L);

        Mockito.when(loanService.loanBook(Mockito.anyLong(), Mockito.anyLong()))
                .thenThrow(new StateException("exception.loan.maxLoansReached", null));

        mockMvc.perform(post("/api/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Maximum number of loans reached for this member."));
    }

    @Test
    void loanBook_ShouldReturnBadRequest_WhenOverdueLoansExist() throws Exception {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(1L);
        loanRequest.setMemberId(1L);

        Mockito.when(loanService.loanBook(Mockito.anyLong(), Mockito.anyLong()))
                .thenThrow(new StateException("exception.loan.overdueExists", null));

        mockMvc.perform(post("/api/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Member has overdue loans and cannot borrow new books."));
    }

}

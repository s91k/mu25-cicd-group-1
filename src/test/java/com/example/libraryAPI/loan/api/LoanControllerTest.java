package com.example.libraryAPI.loan.api;

import com.example.libraryAPI.loan.domain.Loan;
import com.example.libraryAPI.loan.service.BookItemAlreadyBorrowedException;
import com.example.libraryAPI.loan.service.LoanNotFoundException;
import com.example.libraryAPI.loan.service.LoanService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoanService loanService;

    @Test
    void borrowBook_shouldReturnCreatedLoan() throws Exception {
        Loan loan = new Loan(
                1,
                22,
                333,
                LocalDate.now(),
                null
        );

        when(loanService.borrowBook(22, 333))
                .thenReturn(loan);

        CreateLoanRequest request = new CreateLoanRequest(22, 333);

        mockMvc.perform(post("/loans")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.bookItemId").value(22))
               .andExpect(jsonPath("$.borrowerId").value(333))
               .andExpect(jsonPath("$.returnedDate").doesNotExist());

        verify(loanService, times(1)).borrowBook(22, 333);
    }

    @Test
    void returnBook_shouldReturnUpdatedLoan() throws Exception {
        Loan loan = new Loan(
                1,
                22,
                333,
                LocalDate.now()
                         .minusDays(5),
                LocalDate.now()
        );

        when(loanService.returnBook(1))
                .thenReturn(loan);

        mockMvc.perform(post("/loans/1/return"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.returnedDate")
                       .value(LocalDate.now()
                                       .toString()));

        verify(loanService, times(1)).returnBook(1);
    }

    @Test
    void getLoan_shouldReturnLoan() throws Exception {
        Loan loan = new Loan(
                1,
                22,
                333,
                LocalDate.now(),
                null
        );

        when(loanService.getLoan(1))
                .thenReturn(loan);

        mockMvc.perform(get("/loans/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.bookItemId").value(22))
               .andExpect(jsonPath("$.borrowerId").value(333));

        verify(loanService, times(1)).getLoan(1);
    }

    @Test
    void getAllLoans_shouldReturnAllLoans() throws Exception {
        Loan loan1 = new Loan(
                1,
                22,
                333,
                LocalDate.now(),
                null
        );

        Loan loan2 = new Loan(
                501,
                66,
                777,
                LocalDate.now(),
                null
        );

        when(loanService.getAllLoans())
                .thenReturn(List.of(loan1, loan2));

        mockMvc.perform(get("/loans"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[1].id").value(501));

        verify(loanService, times(1)).getAllLoans();
    }

    @Test
    void getLoansByBookItemId_shouldReturnMatchingLoans() throws Exception {
        Loan loan = new Loan(
                1,
                22,
                333,
                LocalDate.now(),
                null
        );

        when(loanService.getLoansByBookItemId(22))
                .thenReturn(List.of(loan));

        mockMvc.perform(get("/loans/book-item/22"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].bookItemId").value(22));

        verify(loanService, times(1)).getLoansByBookItemId(22);
    }

    @Test
    void getLoan_shouldReturn404WhenLoanDoesNotExist() throws Exception {
        when(loanService.getLoan(99))
                .thenThrow(new LoanNotFoundException(99));

        mockMvc.perform(get("/loans/99"))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Loan not found: 99"));

        verify(loanService, times(1)).getLoan(99);
    }

    @Test
    void borrowBook_shouldReturn400WhenBookItemAlreadyBorrowed() throws Exception {
        when(loanService.borrowBook(10, 20))
                .thenThrow(new BookItemAlreadyBorrowedException(10));

        CreateLoanRequest request = new CreateLoanRequest(10, 20);

        mockMvc.perform(post("/loans")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Book item is already borrowed: 10"));

        verify(loanService, times(1)).borrowBook(10, 20);
    }
}

package com.example.libraryAPI.service;

import com.example.libraryAPI.exception.BookItemAlreadyBorrowedException;
import com.example.libraryAPI.exception.LoanAlreadyReturnedException;
import com.example.libraryAPI.exception.LoanNotFoundException;
import com.example.libraryAPI.model.Loan;
import com.example.libraryAPI.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanRepository repository;

    @InjectMocks
    private LoanServiceImpl service;

    @Test
    void borrowBook_shouldCreateLoan() {
        when(repository.findByBookItemId(10))
                .thenReturn(List.of());

        when(repository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Loan loan = service.borrowBook(10, 20);

        assertEquals(10, loan.bookItemId());
        assertEquals(20, loan.borrowerId());
        assertEquals(LocalDate.now(), loan.borrowedDate());
        assertNull(loan.returnedDate());

        verify(repository, times(1)).save(any(Loan.class));
    }

    @Test
    void borrowBook_shouldThrowWhenBookItemAlreadyBorrowed() {
        Loan existingLoan = new Loan(
                1,
                19,
                23,
                LocalDate.now(),
                null
        );

        when(repository.findByBookItemId(19))
                .thenReturn(List.of(existingLoan));

        assertThrows(
                BookItemAlreadyBorrowedException.class,
                () -> service.borrowBook(19, 31)
        );

        verify(repository, times(0)).save(any(Loan.class));
    }

    @Test
    void returnBook_shouldSetReturnedDate() {
        Loan existingLoan = new Loan(
                123,
                14,
                29,
                LocalDate.now()
                         .minusDays(3),
                null
        );

        when(repository.findById(123))
                .thenReturn(Optional.of(existingLoan));

        when(repository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Loan returnedLoan = service.returnBook(123);

        assertEquals(123, returnedLoan.id());
        assertEquals(LocalDate.now(), returnedLoan.returnedDate());

        verify(repository, times(1)).save(any(Loan.class));
    }

    @Test
    void returnBook_shouldThrowWhenLoanAlreadyReturned() {
        Loan existingLoan = new Loan(
                117,
                13,
                20,
                LocalDate.now()
                         .minusDays(5),
                LocalDate.now()
                         .minusDays(2)
        );

        when(repository.findById(117))
                .thenReturn(Optional.of(existingLoan));

        assertThrows(
                LoanAlreadyReturnedException.class,
                () -> service.returnBook(117)
        );

        verify(repository, times(0)).save(any(Loan.class));
    }

    @Test
    void getLoan_shouldThrowWhenLoanDoesNotExist() {
        when(repository.findById(69))
                .thenReturn(Optional.empty());

        assertThrows(
                LoanNotFoundException.class,
                () -> service.getLoan(69)
        );
    }

    @Test
    void getAllLoans_shouldReturnAllLoans() {
        Loan loan1 = new Loan(
                101,
                81,
                20,
                LocalDate.now()
                         .minusDays(3),
                null
        );

        Loan loan2 = new Loan(
                102,
                91,
                21,
                LocalDate.now()
                         .minusDays(5),
                LocalDate.now()
                         .minusDays(1)
        );

        when(repository.findAll())
                .thenReturn(List.of(loan1, loan2));

        List<Loan> loans = service.getAllLoans();

        assertEquals(2, loans.size());
        assertEquals(loan1, loans.get(0));
        assertEquals(loan2, loans.get(1));

        verify(repository, times(1)).findAll();
    }

    @Test
    void getLoansByBookItemId_shouldReturnMatchingLoans() {
        Loan loan1 = new Loan(
                113,
                18,
                19,
                LocalDate.now()
                         .minusDays(5),
                LocalDate.now()
                         .minusDays(2)
        );

        Loan loan2 = new Loan(
                218,
                18,
                19,
                LocalDate.now(),
                null
        );

        when(repository.findByBookItemId(18))
                .thenReturn(List.of(loan1, loan2));

        List<Loan> loans = service.getLoansByBookItemId(18);

        assertEquals(2, loans.size());
        assertEquals(loan1, loans.get(0));
        assertEquals(loan2, loans.get(1));

        verify(repository, times(1)).findByBookItemId(18);
    }

    @Test
    void getLoan_shouldReturnLoan() {
        Loan existingLoan = new Loan(
                111,
                33,
                44,
                LocalDate.now()
                         .minusDays(3),
                null
        );

        when(repository.findById(111))
                .thenReturn(Optional.of(existingLoan));

        Loan loan = service.getLoan(111);

        assertEquals(existingLoan, loan);

        verify(repository, times(1)).findById(111);
    }

    @Test
    void borrowBook_shouldAllowBorrowingAgainAfterPreviousLoanWasReturned() {
        Loan previousLoan = new Loan(
                111,
                222,
                333,
                LocalDate.now()
                         .minusDays(10),
                LocalDate.now()
                         .minusDays(5)
        );

        when(repository.findByBookItemId(222))
                .thenReturn(List.of(previousLoan));

        when(repository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Loan newLoan = service.borrowBook(222, 333);

        assertEquals(222, newLoan.bookItemId());
        assertEquals(333, newLoan.borrowerId());
        assertNull(newLoan.returnedDate());

        verify(repository, times(1)).save(any(Loan.class));
    }
}

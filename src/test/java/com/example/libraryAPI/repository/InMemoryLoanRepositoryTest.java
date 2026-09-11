package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryLoanRepositoryTest {

    private InMemoryLoanRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryLoanRepository();
    }

    @Test
    void save_shouldGenerateIdForNewLoan() {
        Loan loan = new Loan(
                0,
                10,
                20,
                LocalDate.now(),
                null
        );

        Loan savedLoan = repository.save(loan);

        assertEquals(0, savedLoan.id());
        assertEquals(10, savedLoan.bookItemId());
        assertEquals(20, savedLoan.borrowerId());
    }

    @Test
    void save_shouldGenerateDifferentIdsForNewLoans() {
        Loan loan1 = new Loan(
                0,
                10,
                20,
                LocalDate.now(),
                null
        );

        Loan loan2 = new Loan(
                0,
                11,
                21,
                LocalDate.now(),
                null
        );

        Loan savedLoan1 = repository.save(loan1);
        Loan savedLoan2 = repository.save(loan2);

        assertEquals(0, savedLoan1.id());
        assertEquals(1, savedLoan2.id());
    }

    @Test
    void save_shouldKeepExistingId() {
        Loan loan = new Loan(
                5,
                10,
                20,
                LocalDate.now(),
                null
        );

        Loan savedLoan = repository.save(loan);

        assertEquals(5, savedLoan.id());
    }

    @Test
    void save_shouldUpdateExistingLoan() {
        Loan loan = new Loan(
                1,
                10,
                20,
                LocalDate.now()
                         .minusDays(5),
                null
        );

        repository.save(loan);

        Loan returnedLoan = new Loan(
                1,
                10,
                20,
                loan.borrowedDate(),
                LocalDate.now()
        );

        repository.save(returnedLoan);

        Optional<Loan> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals(returnedLoan, result.get());
    }

    @Test
    void findById_shouldReturnLoan() {
        Loan loan = new Loan(
                1,
                10,
                20,
                LocalDate.now(),
                null
        );

        repository.save(loan);

        Optional<Loan> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals(loan, result.get());
    }

    @Test
    void findById_shouldReturnEmptyWhenLoanDoesNotExist() {
        Optional<Loan> result = repository.findById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllLoans() {
        Loan loan1 = new Loan(
                1,
                10,
                20,
                LocalDate.now(),
                null
        );

        Loan loan2 = new Loan(
                2,
                11,
                21,
                LocalDate.now(),
                null
        );

        repository.save(loan1);
        repository.save(loan2);

        List<Loan> loans = repository.findAll();

        assertEquals(2, loans.size());
        assertTrue(loans.contains(loan1));
        assertTrue(loans.contains(loan2));
    }

    @Test
    void findAll_shouldReturnEmptyListWhenRepositoryIsEmpty() {
        List<Loan> loans = repository.findAll();

        assertTrue(loans.isEmpty());
    }

    @Test
    void findByBookItemId_shouldReturnMatchingLoans() {
        Loan loan1 = new Loan(
                1,
                10,
                20,
                LocalDate.now()
                         .minusDays(10),
                LocalDate.now()
                         .minusDays(5)
        );

        Loan loan2 = new Loan(
                2,
                10,
                21,
                LocalDate.now(),
                null
        );

        Loan otherLoan = new Loan(
                3,
                11,
                22,
                LocalDate.now(),
                null
        );

        repository.save(loan1);
        repository.save(loan2);
        repository.save(otherLoan);

        List<Loan> loans = repository.findByBookItemId(10);

        assertEquals(2, loans.size());
        assertTrue(loans.contains(loan1));
        assertTrue(loans.contains(loan2));
        assertFalse(loans.contains(otherLoan));
    }

    @Test
    void findByBookItemId_shouldReturnEmptyListWhenNoLoansMatch() {
        Loan loan = new Loan(
                1,
                10,
                20,
                LocalDate.now(),
                null
        );

        repository.save(loan);

        List<Loan> loans = repository.findByBookItemId(99);

        assertTrue(loans.isEmpty());
    }
}


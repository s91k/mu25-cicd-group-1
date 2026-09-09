package com.example.libraryAPI.service;

import com.example.libraryAPI.exception.BookItemAlreadyBorrowedException;
import com.example.libraryAPI.exception.LoanAlreadyReturnedException;
import com.example.libraryAPI.exception.LoanNotFoundException;
import com.example.libraryAPI.model.Loan;
import com.example.libraryAPI.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan borrowBook(int bookItemId, int borrowerId) {
        boolean alreadyBorrowed = repository.findByBookItemId(bookItemId)
                                            .stream()
                                            .anyMatch(loan -> loan.returnedDate() == null);

        if (alreadyBorrowed) {
            throw new BookItemAlreadyBorrowedException(bookItemId);
        }

        return repository.save(new Loan(
                0,
                bookItemId,
                borrowerId,
                LocalDate.now(),
                null
        ));
    }

    @Override
    public Loan returnBook(int loanId) {
        Loan loan = getLoan(loanId);

        if (loan.returnedDate() != null) {
            throw new LoanAlreadyReturnedException(loanId);
        }

        return repository.save(new Loan(
                loan.id(),
                loan.bookItemId(),
                loan.borrowerId(),
                loan.borrowedDate(),
                LocalDate.now()
        ));
    }

    @Override
    public Loan getLoan(int id) {
        return repository.findById(id)
                         .orElseThrow(() -> new LoanNotFoundException(id));
    }

    @Override
    public List<Loan> getAllLoans() {
        return repository.findAll();
    }

    @Override
    public List<Loan> getLoansByBookItemId(int bookItemId) {
        return repository.findByBookItemId(bookItemId);
    }
}
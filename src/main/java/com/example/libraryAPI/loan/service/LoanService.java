package com.example.libraryAPI.loan.service;

import com.example.libraryAPI.loan.domain.Loan;

import java.util.List;

public interface LoanService {

    Loan borrowBook(int bookItemId, int borrowerId);

    Loan returnBook(int loanId);

    Loan getLoan(int id);

    List<Loan> getAllLoans();

    List<Loan> getLoansByBookItemId(int bookItemId);
}

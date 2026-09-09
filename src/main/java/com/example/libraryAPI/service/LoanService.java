package com.example.libraryAPI.service;

import com.example.libraryAPI.model.Loan;

import java.util.List;

public interface LoanService {

    Loan borrowBook(int bookItemId, int borrowerId);

    Loan returnBook(int loanId);

    Loan getLoan(int id);

    List<Loan> getAllLoans();

    List<Loan> getLoansByBookItemId(int bookItemId);
}

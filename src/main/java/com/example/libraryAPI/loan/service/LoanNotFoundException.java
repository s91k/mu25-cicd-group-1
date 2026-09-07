package com.example.libraryAPI.loan.service;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(int id) {
        super("Loan not found: " + id);
    }
}

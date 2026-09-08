package com.example.libraryAPI.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(int id) {
        super("Loan not found: " + id);
    }
}

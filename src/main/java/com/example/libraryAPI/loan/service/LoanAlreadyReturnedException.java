package com.example.libraryAPI.loan.service;

public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException(int loanId) {
        super("Loan is already returned: " + loanId);
    }
}

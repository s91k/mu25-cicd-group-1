package com.example.libraryAPI.exception;

public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException(int loanId) {
        super("Loan is already returned: " + loanId);
    }
}

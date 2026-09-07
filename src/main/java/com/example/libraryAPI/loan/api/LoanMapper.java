package com.example.libraryAPI.loan.api;

import com.example.libraryAPI.loan.domain.Loan;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static LoanResponse toResponse(Loan loan) {
        return new LoanResponse(
                loan.id(),
                loan.bookItemId(),
                loan.borrowerId(),
                loan.borrowedDate(),
                loan.returnedDate()
        );
    }
}

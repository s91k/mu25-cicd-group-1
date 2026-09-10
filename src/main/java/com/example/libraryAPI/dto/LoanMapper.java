package com.example.libraryAPI.dto;

import com.example.libraryAPI.model.Loan;

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

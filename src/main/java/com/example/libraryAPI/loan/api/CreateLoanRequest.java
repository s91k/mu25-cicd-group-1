package com.example.libraryAPI.loan.api;

public record CreateLoanRequest(
        int bookItemId,
        int borrowerId
) {
}

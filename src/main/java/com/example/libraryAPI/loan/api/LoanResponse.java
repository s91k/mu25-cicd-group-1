package com.example.libraryAPI.loan.api;

import java.time.LocalDate;

public record LoanResponse(
        int id,
        int bookItemId,
        int borrowerId,
        LocalDate borrowedDate,
        LocalDate returnedDate
) {
}

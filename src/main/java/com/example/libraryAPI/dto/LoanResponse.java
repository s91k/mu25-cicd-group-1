package com.example.libraryAPI.dto;

import java.time.LocalDate;

public record LoanResponse(
        int id,
        int bookItemId,
        int borrowerId,
        LocalDate borrowedDate,
        LocalDate returnedDate
) {
}

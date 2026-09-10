package com.example.libraryAPI.model;

import java.time.LocalDate;

public record Loan(
        int id,
        int bookItemId,
        int borrowerId,
        LocalDate borrowedDate,
        LocalDate returnedDate
) {
}
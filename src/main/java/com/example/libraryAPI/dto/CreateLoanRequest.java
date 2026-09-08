package com.example.libraryAPI.dto;

public record CreateLoanRequest(
        int bookItemId,
        int borrowerId
) {
}

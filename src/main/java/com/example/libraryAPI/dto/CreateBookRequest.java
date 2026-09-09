package com.example.libraryAPI.dto;

public record CreateBookRequest(
        String title,
        int authorId
) {
}

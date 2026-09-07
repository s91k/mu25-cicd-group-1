package com.example.libraryAPI.loan.service;

public class BookItemAlreadyBorrowedException extends RuntimeException {

    public BookItemAlreadyBorrowedException(int bookItemId) {
        super("Book item is already borrowed: " + bookItemId);
    }
}

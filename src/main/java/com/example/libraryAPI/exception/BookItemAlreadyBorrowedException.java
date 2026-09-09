package com.example.libraryAPI.exception;

public class BookItemAlreadyBorrowedException extends RuntimeException {

    public BookItemAlreadyBorrowedException(int bookItemId) {
        super("Book item is already borrowed: " + bookItemId);
    }
}

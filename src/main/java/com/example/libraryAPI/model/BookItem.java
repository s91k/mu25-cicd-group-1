package com.example.libraryAPI.model;

public class BookItem {

    private int id;
    private int bookId;

    public BookItem(int id, int bookId) {
        this.id = id;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }
}

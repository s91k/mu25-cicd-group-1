package com.example.libraryAPI.model;

public class BookItem {

    private Long id;
    private Long bookId;

    public BookItem(Long id, Long bookId) {
        this.id = id;
        this.bookId = bookId;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }
}

package com.example.libraryAPI.dto;

public class CreateBookRequest {

    private String title;
    private int authorId;

    public CreateBookRequest() {
    }

    public CreateBookRequest(String title, int authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }
}

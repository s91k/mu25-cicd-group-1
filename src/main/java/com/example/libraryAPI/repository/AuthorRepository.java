package com.example.libraryAPI.repository;

import com.example.libraryAPI.entity.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepository {

    private final List<Author> authors = new ArrayList<>();

    public List<Author> getAll() {
        return authors;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }
}

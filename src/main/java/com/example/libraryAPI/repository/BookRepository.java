package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        books.add(new Book(1, "The Hobbit", 1));
        books.add(new Book(2, "1984", 2));
        books.add(new Book(3, "Twilight", 3));
    }

    public List<Book> findAll() {
        return books;
    }

    public Book findById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Book save(Book book) {
        int newId = books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0) + 1;

        Book savedBook = new Book(
                newId,
                book.getTitle(),
                book.getAuthorId()
        );
        books.add(savedBook);
        return savedBook;
    }
}

package com.example.libraryAPI.service;

import com.example.libraryAPI.model.BookItem;
import com.example.libraryAPI.repository.BookItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookItemService {

    private final BookItemRepository repository;

    public BookItemService(BookItemRepository repository) {
        this.repository = repository;
    }

    public BookItem create(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException(
                    "bookId must be a positive number."
            );
        }
        return repository.save(bookId);
    }

    public List<BookItem> findAll() {
        return repository.findAll();
    }

    public BookItem findById(int id) {
        return repository.findById(id);
    }
}

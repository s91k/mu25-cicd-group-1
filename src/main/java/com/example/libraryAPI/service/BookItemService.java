package com.example.libraryAPI.service;

import com.example.libraryAPI.model.BookItem;
import com.example.libraryAPI.repository.BookItemRepository;
import com.example.libraryAPI.repository.BookRepository;
import com.example.libraryAPI.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookItemService {

    private final BookItemRepository repository;
    private final BookRepository bookRepository;

    public BookItemService(BookItemRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    public BookItem create(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException(
                    "bookId must be a positive number."
            );
        }

        if (bookRepository.findById(bookId) == null) {
            throw new ResourceNotFoundException(
                    "Book not found: " + bookId
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

package com.example.libraryAPI.service;

import com.example.libraryAPI.model.BookItem;
import com.example.libraryAPI.repository.BookItemRepository;
import com.example.libraryAPI.repository.BookRepository;
import com.example.libraryAPI.model.Book;
import com.example.libraryAPI.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookItemServiceTest {

    private BookItemRepository repository;
    private BookItemService service;
    private int bookId;

    @BeforeEach
    void setUp() {
        repository = new BookItemRepository();
        BookRepository bookRepository = new BookRepository();

        Book book = bookRepository.save(new Book(0, "Testbok", 1));
        bookId = book.getId();

        service = new BookItemService(repository, bookRepository);
    }

    @Test
    void validBookIdCreatesItem() {
        BookItem item = service.create(bookId);

        assertTrue(item.getId() > 0);
        assertEquals(bookId, item.getBookId());
        assertEquals(item, repository.findById(item.getId()));
    }

    @Test
    void missingBookDoesNotSaveItem() {
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.create(999)
        );

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void invalidBookIdDoesNotSaveAnything() {
        assertThrows(IllegalArgumentException.class, () -> service.create(0));
        assertThrows(IllegalArgumentException.class, () -> service.create(-1));

        assertTrue(repository.findAll().isEmpty());
    }
}

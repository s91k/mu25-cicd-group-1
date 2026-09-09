package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryTest {

    @Test
    void shouldFindBookById() {
        BookRepository repository = new BookRepository();

        Book result = repository.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void shouldReturnNullWhenBooKDoesNotExist() {
        BookRepository repository = new BookRepository();

        Book result = repository.findById(99);

        assertNull(result);
    }
}

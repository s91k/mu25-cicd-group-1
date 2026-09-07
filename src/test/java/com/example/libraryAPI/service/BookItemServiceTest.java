package com.example.libraryAPI.service;

import com.example.libraryAPI.model.BookItem;
import com.example.libraryAPI.repository.BookItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookItemServiceTest {

    private BookItemRepository repository;
    private BookItemService service;

    @BeforeEach
    void setUp() {
        repository = new BookItemRepository();
        service = new BookItemService(repository);
    }

    @Test
    void validBookIdCreatesItem() {
        BookItem item = service.create(10L);

        assertNotNull(item.getId());
        assertEquals(10L, item.getBookId());
        assertEquals(item, repository.findById(item.getId()));
    }

    @Test
    void invalidBookIdDoesNotSaveAnything() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
        assertThrows(IllegalArgumentException.class, () -> service.create(0L));
        assertThrows(IllegalArgumentException.class, () -> service.create(-1L));

        assertTrue(repository.findAll().isEmpty());
    }
}

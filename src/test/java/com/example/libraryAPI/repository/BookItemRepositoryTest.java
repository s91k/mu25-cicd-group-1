package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.BookItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookItemRepositoryTest {

    private BookItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BookItemRepository();
    }

    @Test
    void savedItemCanBeFound() {
        BookItem item = repository.save(10L);

        BookItem found = repository.findById(item.getId());

        assertNotNull(found);
        assertEquals(item.getId(), found.getId());
        assertEquals(10L, found.getBookId());
    }

    @Test
    void copiesGetUniqueIdsAndAreBothStored() {
        BookItem first = repository.save(10L);
        BookItem second = repository.save(10L);

        assertNotEquals(first.getId(), second.getId());

        List<BookItem> items = repository.findAll();
        assertEquals(2, items.size());
        assertTrue(items.contains(first));
        assertTrue(items.contains(second));
    }

    @Test
    void missingIdReturnsNull() {
        assertNull(repository.findById(99L));
    }
}

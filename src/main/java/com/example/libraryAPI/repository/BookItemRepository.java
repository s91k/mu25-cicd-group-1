package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.BookItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookItemRepository {

    private final Map<Long, BookItem> items = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public BookItem save(Long bookId) {
        Long id = nextId.getAndIncrement();
        BookItem item = new BookItem(id, bookId);
        items.put(id, item);
        return item;
    }

    public List<BookItem> findAll() {
        return List.copyOf(items.values());
    }

    public BookItem findById(Long id) {
        return items.get(id);
    }

}

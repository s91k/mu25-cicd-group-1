package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.BookItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BookItemRepository {

    private final Map<Integer, BookItem> items = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public BookItem save(int bookId) {
        int id = nextId.getAndIncrement();
        BookItem item = new BookItem(id, bookId);
        items.put(id, item);
        return item;
    }

    public List<BookItem> findAll() {
        return List.copyOf(items.values());
    }

    public BookItem findById(int id) {
        return items.get(id);
    }

}

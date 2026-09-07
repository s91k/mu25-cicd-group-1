package com.example.libraryAPI.controller;

import com.example.libraryAPI.model.BookItem;
import com.example.libraryAPI.model.CreateBookItemRequest;
import com.example.libraryAPI.service.BookItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/book-items")
public class BookItemController {

    private final BookItemService service;

    public BookItemController(BookItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookItem> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookItem> findById(@PathVariable Long id) {
        BookItem item = service.findById(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<BookItem> create(
            @RequestBody CreateBookItemRequest request) {

        if (request.getBookId() == null || request.getBookId() <= 0) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "BookId must be a positive number"
            );
        }

        BookItem item = service.create(request.getBookId());

        return ResponseEntity
                .created(URI.create("/book-items/" + item.getId()))
                .body(item);
    }
}

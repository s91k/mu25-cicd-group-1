package com.example.libraryAPI.controller;

import com.example.libraryAPI.entity.Author;
import com.example.libraryAPI.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    public AuthorController() {

    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getById(@PathVariable int id){
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> create(@RequestBody Author author){
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updated(@PathVariable int id, @RequestBody Author updatedAuthor){
        return ResponseEntity.ok(authorService.updateAuthor(id, updatedAuthor));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
       return ResponseEntity.ok(authorService.deleteAuthor(id));
    }
}

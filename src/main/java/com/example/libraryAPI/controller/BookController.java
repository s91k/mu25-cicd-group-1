package com.example.libraryAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    public BookController(){

    }

    @GetMapping("/books")
    public ResponseEntity<String> getAll() {
        return ResponseEntity.ok("Test");
    }
}

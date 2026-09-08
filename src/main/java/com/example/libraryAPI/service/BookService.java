package com.example.libraryAPI.service;

import com.example.libraryAPI.dto.CreateBookRequest;
import com.example.libraryAPI.model.Book;
import com.example.libraryAPI.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        Book book = bookRepository.findById(id);

        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }

        return book;
    }

    public Book createBook(CreateBookRequest request) {
        Book book = new Book(
                0,
                request.getTitle(),
                request.getAuthorId()
        );
        return bookRepository.save(book);
    }


}

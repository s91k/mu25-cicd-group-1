package com.example.libraryAPI.service;

import com.example.libraryAPI.dto.CreateBookRequest;
import com.example.libraryAPI.exception.AuthorNotFoundException;
import com.example.libraryAPI.model.Author;
import com.example.libraryAPI.model.Book;
import com.example.libraryAPI.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
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
        Author author = authorService.getById(request.authorId());

        if (author == null) {
            throw new AuthorNotFoundException("Author not found.");
        }

        Book book = new Book(
                0,
                request.title(),
                request.authorId()
        );
        return bookRepository.save(book);
    }


}

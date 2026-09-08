package com.example.libraryAPI.service;

import com.example.libraryAPI.dto.CreateBookRequest;
import com.example.libraryAPI.exception.AuthorNotFoundException;
import com.example.libraryAPI.model.Author;
import com.example.libraryAPI.model.Book;
import com.example.libraryAPI.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() {
        // arrange
        List<Book> books = List.of(
                new Book(1, "The Hobbit", 1),
                new Book(2, "1984", 2)
        );

        when(bookRepository.findAll()).thenReturn(books);

        // act & assert
        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("The Hobbit", result.get(0).getTitle());

        verify(bookRepository).findAll();
    }

    @Test
    void shouldReturnBookWhenBookExists() {
        // Arrange
        Book book = new Book(1, "The Hobbit", 1);

        when(bookRepository.findById(1)).thenReturn(book);

        // Act & assert
        Book result = bookService.getBookById(1);

        assertEquals(1, result.getId());
        assertEquals("The Hobbit", result.getTitle());
        assertEquals(1, result.getAuthorId());

        verify(bookRepository).findById(1);
    }

    @Test
    void shouldThrowWhenBookDoesNotExist() {
        // Arrange
        when(bookRepository.findById(99)).thenReturn(null);

        // ACt & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> bookService.getBookById(99)
        );

        verify(bookRepository).findById(99);
    }

    @Test
    void shouldCreateBook() {
        // Arrangte
        CreateBookRequest request = new CreateBookRequest("Dune", 3);

        Author author = new Author(3, "Frank", "Herbert");

        Book savedBook = new Book(4, "Dune", 3);

        when(authorService.getById(3)).thenReturn(author);

        when(bookRepository.save(any(Book.class)))
                .thenReturn(savedBook);

        // Act & Assert
        Book result = bookService.createBook(request);

        assertEquals(4, result.getId());
        assertEquals("Dune", result.getTitle());
        assertEquals(3, result.getAuthorId());

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void shouldThrowWhenAuthorDoesNotExist() {
        CreateBookRequest request = new CreateBookRequest("Dune", 99);

        when(authorService.getById(99)).thenReturn(null);

        assertThrows(
                AuthorNotFoundException.class,
                () -> bookService.createBook(request)
        );

        verify(authorService).getById(99);
        verify(bookRepository, never()).save(any(Book.class));
    }
}

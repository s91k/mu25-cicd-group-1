package com.example.libraryAPI;

import com.example.libraryAPI.model.Book;
import com.example.libraryAPI.repository.BookRepository;
import com.example.libraryAPI.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() {
        // arrange
        List<Book> books = List.of(
                new Book(1, "The Hobbit", 1),
                new Book(2, "1984", 2)
                // new Book(3, "Twilight", 3) = error, expected 2
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
}

package com.example.libraryAPI.service;

import com.example.libraryAPI.exception.AuthorNotFoundException;
import com.example.libraryAPI.model.Author;
import com.example.libraryAPI.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void ShouldReturnAllAuthors(){

        // arrange
        List<Author> authors = List.of(new Author("Astrid", "Lindgren"));
        when(authorRepository.getAll()).thenReturn(authors);
        // act
        List<Author> result = authorService.getAllAuthors();

        // assert
        assertEquals(authors, result);
    }

    @Test
    public void ShouldReturnAuthorById(){
        // arrange
        Author expected = new Author(1, "George", "Orwell");
        List<Author> authors = List.of(expected);
        when(authorRepository.getAll()).thenReturn(authors);

        // act
        Author result = authorService.getById(1);

        // assert
        assertEquals(expected, result);
    }

    @Test
    public void ShouldThrowExceptionWhenAuthorNotFound() {
        // arrange
        List<Author> authors = List.of(new Author(1, "George", "Orwell"));
        when(authorRepository.getAll()).thenReturn(authors);

        // act & assert
        assertThrows(AuthorNotFoundException.class, () -> authorService.getById(2));
    }

    @Test
    public void ShouldCreateAuthor(){
        // arrange
        Author newAuthor = new Author("J.K.", "Rowling");
        // act
        Author result = authorService.createAuthor(newAuthor);
        // assert
        assertEquals(newAuthor, result);
        verify(authorRepository).addAuthor(newAuthor);
    }

    @Test
    public void ShouldUpdateAuthor(){
        // arrange
        Author existingAuthor = new Author(1, "George", "Orwell");
        Author updatedAuthor = new Author("Eric", "Blair");
        List<Author> authors = List.of(existingAuthor);
        when(authorRepository.getAll()).thenReturn(authors);

        // act
        Author result = authorService.updateAuthor(1, updatedAuthor);

        // assert
        assertEquals("Eric", result.getFirstName());
        assertEquals("Blair", result.getLastName());
    }

    @Test
    public void ShouldThrowExceptionWhenUpdatingNonExistentAuthor(){
        // arrange
        Author updatedAuthor = new Author("Eric", "Blair");
        List<Author> authors = List.of(new Author(1, "George", "Orwell"));
        when(authorRepository.getAll()).thenReturn(authors);

        // act & assert
        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(2, updatedAuthor));

    }

    @Test
    public void ShouldDeleteAuthor() {
        // arrange
        Author existingAuthor = new Author(1, "George", "Orwell");
        List<Author> authors = List.of(existingAuthor);
        when(authorRepository.getAll()).thenReturn(authors);
        // act
        Author result = authorService.deleteAuthor(1);
        // assert
        assertEquals(existingAuthor, result);
        verify(authorRepository).removeAuthor(existingAuthor);
    }

    @Test
    public void ShouldThrowExceptionWhenDeletingNonExistentAuthor(){
        // arrange
        List<Author> authors = List.of(new Author(1, "George", "Orwell"));
        when(authorRepository.getAll()).thenReturn(authors);

        // act & assert
        assertThrows(AuthorNotFoundException.class, () -> authorService.deleteAuthor(2));
    }
}

package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthorRepositoryTest {
    private AuthorRepository authorRepository = new AuthorRepository();

    @Test
    public void ShouldReturnAllAuthors(){
        // act
        var result = authorRepository.getAll();

        // assert
        assertEquals(3, result.size());
    }

    @Test
    public void ShouldAddAuthor(){
        // arrange
        var newAuthor = new Author("J.K.", "Rowling");

        // act
        authorRepository.addAuthor(newAuthor);

        // assert
        var result = authorRepository.getAll();
        assertEquals(4, result.size());
        assertEquals("J.K.", result.get(3).getFirstName());
    }

    @Test
    public void ShouldRemoveAuthor(){
        // arrange
        var authorToRemove = authorRepository.getAll().get(0);

        // act
        authorRepository.removeAuthor(authorToRemove);

        // assert
        var result = authorRepository.getAll();
        assertEquals(2, result.size());
        assertEquals("George", result.get(0).getFirstName());
    }

}

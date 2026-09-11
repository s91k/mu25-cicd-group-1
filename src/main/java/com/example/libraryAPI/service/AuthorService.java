package com.example.libraryAPI.service;

import com.example.libraryAPI.exception.AuthorNotFoundException;
import com.example.libraryAPI.model.Author;
import com.example.libraryAPI.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.getAll();
    }

    public Author getById(int id){
        return authorRepository.getAll().stream()
                .filter(a->a.getId()==id).findFirst().orElseThrow(()-> new AuthorNotFoundException("Author with id " + id + " not found"));
    }

    public Author createAuthor(Author author){
        authorRepository.addAuthor(author);
        return author;
    }

    public Author updateAuthor(int id, Author updatedAuthor){
        Author existing = authorRepository.getAll().stream()
                .filter(a->a.getId()==id).findFirst().orElseThrow(()-> new AuthorNotFoundException("Author with id " + id + " not found"));

        if (existing != null){
            existing.setFirstName(updatedAuthor.getFirstName());
            existing.setLastName(updatedAuthor.getLastName());
        }
        return existing;
    }

    public Author deleteAuthor(int id){
        Author existing = authorRepository.getAll().stream()
                .filter(a->a.getId()==id).findFirst().orElseThrow(()-> new AuthorNotFoundException("Author with id " + id + " not found"));
        authorRepository.removeAuthor(existing);
        return existing;
    }

}

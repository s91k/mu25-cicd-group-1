package com.example.libraryAPI;

import com.example.libraryAPI.model.Author;
import com.example.libraryAPI.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import com.example.libraryAPI.controller.AuthorController;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @Test
    public void ShouldReturnAllAuthors() throws Exception {
        // Arrange
        when(authorService.getAllAuthors())
                .thenReturn(List.of(new Author(1, "Astrid", "Lindgren")));

        // Act & Assert
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Astrid"));
        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void ShouldReturnAuthorById() throws Exception{
        //Arrange
        when(authorService.getById(1))
                .thenReturn(new Author(1, "Astrid", "Lindgren"));
        // Act & Assert
        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Astrid"));
        verify(authorService, times(1)).getById(1);
    }



    @Test
    public void ShouldCreateAuthor() throws Exception{
        //Arrange
        Author author = new Author(1, "Astrid", "Lindgren");
        String jsonRequest = new ObjectMapper().writeValueAsString(author);

        when(authorService.createAuthor(any(Author.class)))
                .thenReturn(new Author(1, "Astrid", "Lindgren"));

        // Act & Assert
        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Astrid"));
        verify(authorService, times(1)).createAuthor(any(Author.class));
    }


    @Test
    public void ShouldUpdateAuthor() throws Exception {
        // Arrange
        Author updated = new Author(1, "August", "Strindberg");
        when(authorService.updateAuthor(anyInt(), any(Author.class))).thenReturn(updated);

        String jsonRequest = new ObjectMapper().writeValueAsString(updated);

        // Act & Assert
        mockMvc.perform(put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("August"));
        verify(authorService, times(1)).updateAuthor(anyInt(), any(Author.class));
    }


    @Test
    public void ShouldDeleteAuthor() throws Exception{
        //Arrange
        Author deleted = new Author(1, "Astrid", "Lindgren");
        when(authorService.deleteAuthor(1)).thenReturn(deleted);

        // Act & Assert
        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Astrid"));
        verify(authorService, times(1)).deleteAuthor(1);
    }

}

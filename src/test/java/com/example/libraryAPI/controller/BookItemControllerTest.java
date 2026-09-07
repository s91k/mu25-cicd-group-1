package com.example.libraryAPI.controller;

import com.example.libraryAPI.repository.BookItemRepository;
import com.example.libraryAPI.service.BookItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookItemControllerTest {

    private BookItemService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        service = new BookItemService(new BookItemRepository());
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BookItemController(service))
                .build();
    }

    @Test
    void findAllReturnsItems() throws Exception {
        service.create(10);

        mockMvc.perform(get("/book-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].bookId").value(10));
    }

    @Test
    void findAllReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/book-items"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void findByIdReturnsExistingItem() throws Exception {
        service.create(10);

        mockMvc.perform(get("/book-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.bookId").value(10));
    }

    @Test
    void findByIdReturnsNotfoundWhenMissing() throws Exception {
        mockMvc.perform(get("/book-items/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReturnsCreatedItemAndLocation() throws Exception {
        mockMvc.perform(post("/book-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"bookId": 10}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/book-items/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.bookId").value(10));

        mockMvc.perform(get("/book-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(10));
    }

    @Test
    void invalidBookIdReturnsBadRequest() throws Exception {
        String[] invalidRequests = {
                "{}",
                "{\"bookId\": null}",
                "{\"bookId\": 0}",
                "{\"bookId\": -1}"
        };

        for (String json : invalidRequests) {
            mockMvc.perform(post("/book-items")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest());
        }

        assertTrue(service.findAll().isEmpty());
    }
}

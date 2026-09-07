package com.example.libraryAPI;

import tools.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_returnsCreatedUser() throws Exception {
        String body = objectMapper.writeValueAsString(new TestUserRequest("Karl", "Karlsson"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Karl"))
                .andExpect(jsonPath("$.lastName").value("Karlsson"));
    }

    @Test
    void createUser_withBlankFirstName_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(new TestUserRequest("", "Karlsson"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_withBlankLastName_returnsBadRequest() throws Exception {
        String body = objectMapper.writeValueAsString(new TestUserRequest("Karl", ""));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllUsers_returnsOk() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserById_whenExists_returnsUser() throws Exception {
        String createBody = objectMapper.writeValueAsString(new TestUserRequest("Erik", "Eriksson"));
        String createResponse = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andReturn().getResponse().getContentAsString();
        long id = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value("Erik"));
    }

    @Test
    void getUserById_whenMissing_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_returnsUpdatedUser() throws Exception {
        String createBody = objectMapper.writeValueAsString(new TestUserRequest("Sven", "Svensson"));
        String createResponse = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andReturn().getResponse().getContentAsString();
        long id = objectMapper.readTree(createResponse).get("id").asLong();

        String updateBody = objectMapper.writeValueAsString(new TestUserRequest("Sven", "Andersson"));

        mockMvc.perform(put("/api/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Andersson"));
    }

    @Test
    void updateUser_whenMissing_returnsNotFound() throws Exception {
        String updateBody = objectMapper.writeValueAsString(new TestUserRequest("Ghost", "User"));

        mockMvc.perform(put("/api/users/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_removesUser() throws Exception {
        String createBody = objectMapper.writeValueAsString(new TestUserRequest("Anna", "Andersson"));
        String createResponse = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andReturn().getResponse().getContentAsString();
        long id = objectMapper.readTree(createResponse).get("id").asLong();

        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_whenMissing_returnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/users/999999"))
                .andExpect(status().isNotFound());
    }

    private record TestUserRequest(String firstName, String lastName) {
    }
}

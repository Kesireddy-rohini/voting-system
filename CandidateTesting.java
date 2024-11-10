package com.votingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votingsystem.entity.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class candidateTesting {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String CANDIDATE_URL = "/candidates";

    @BeforeEach
    void setUp() {
        // Add initial data setup if needed
    }

    @Test
    @WithMockUser // Simulate an authenticated user
    public void testAddCandidate_Success() throws Exception {
        Candidate candidate = new Candidate("John Doe", "Independent", 45, "I aim to bring positive change.");

        mockMvc.perform(post(CANDIDATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser
    public void testAddCandidate_MissingFields() throws Exception {
        String candidateJson = "{\"name\":\"Jane Doe\"}";

        mockMvc.perform(post(CANDIDATE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(candidateJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testGetCandidate_Success() throws Exception {
        // Assuming candidate with ID 1 exists
        mockMvc.perform(get(CANDIDATE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe")); // Adjust based on your actual data
    }

    @Test
    @WithMockUser
    public void testGetCandidate_NotFound() throws Exception {
        mockMvc.perform(get(CANDIDATE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testUpdateCandidate_Success() throws Exception {
        Candidate updatedCandidate = new Candidate("John Doe", "Democratic", 46, "I aim to bring positive change for all.");

        mockMvc.perform(put(CANDIDATE_URL + "/1") // Assuming ID 1 exists
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCandidate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.party").value("Democratic"));
    }

    @Test
    @WithMockUser
    public void testUpdateCandidate_NotFound() throws Exception {
        Candidate updatedCandidate = new Candidate("John Doe", "Democratic", 46, "I aim to bring positive change for all.");

        mockMvc.perform(put(CANDIDATE_URL + "/999") // ID does not exist
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCandidate)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testDeleteCandidate_Success() throws Exception {
        mockMvc.perform(delete(CANDIDATE_URL + "/1")) // Assuming ID 1 exists
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void testDeleteCandidate_NotFound() throws Exception {
        mockMvc.perform(delete(CANDIDATE_URL + "/999")) // ID does not exist
                .andExpect(status().isNotFound());
    }
}
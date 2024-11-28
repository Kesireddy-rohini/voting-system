package com.votingsystem.controller;

import com.votingsystem.entity.Candidate;
import com.votingsystem.service.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateControllerTest {

    @Mock
    private CandidateService candidateService;

    @InjectMocks
    private CandidateController candidateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCandidates() {
        // Arrange
        List<Candidate> mockCandidates = Arrays.asList(
                new Candidate(1L, "John Doe", "Party A"),
                new Candidate(2L, "Jane Doe", "Party B")
        );
        when(candidateService.getAllCandidates()).thenReturn(mockCandidates);

        // Act
        ResponseEntity<List<Candidate>> response = candidateController.getAllCandidates();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(candidateService, times(1)).getAllCandidates();
    }

    @Test
    void testGetCandidateById() {
        // Arrange
        Candidate mockCandidate = new Candidate(1L, "John Doe", "Party A");
        when(candidateService.getCandidateById(1L)).thenReturn(mockCandidate);

        // Act
        ResponseEntity<Candidate> response = candidateController.getCandidateById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(candidateService, times(1)).getCandidateById(1L);
    }

   

    @Test
    void testDeleteCandidate() {
        // Arrange
        when(candidateService.deleteCandidateById(1L)).thenReturn(true);

        // Act
        ResponseEntity<String> response = candidateController.deleteCandidate(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Candidate deleted successfully.", response.getBody());
        verify(candidateService, times(1)).deleteCandidateById(1L);
    }

    @Test
    void testDeleteCandidateNotFound() {
        // Arrange
        when(candidateService.deleteCandidateById(99L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = candidateController.deleteCandidate(99L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Candidate not found.", response.getBody());
        verify(candidateService, times(1)).deleteCandidateById(99L);
    }
}

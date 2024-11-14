package com.votingsystem.service;
import com.votingsystem.service.CandidateService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.votingsystem.entity.Candidate;
import com.votingsystem.repository.CandidateRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CandidateServiceTest {

    @InjectMocks
    private CandidateService candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCandidates() {
        // Arrange
        Candidate candidate1 = new Candidate();
        candidate1.setId(1L);
        candidate1.setName("Candidate One");

        Candidate candidate2 = new Candidate();
        candidate2.setId(2L);
        candidate2.setName("Candidate Two");

        when(candidateRepository.findAll()).thenReturn(Arrays.asList(candidate1, candidate2));

        // Act
        List<Candidate> candidates = candidateService.getAllCandidates();

        // Assert
        assertNotNull(candidates);
        assertEquals(2, candidates.size());
        assertEquals("Candidate One", candidates.get(0).getName());
    }

    @Test
    public void testGetCandidateById_CandidateExists() {
        // Arrange
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("Candidate One");

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));

        // Act
        Candidate result = candidateService.getCandidateById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Candidate One", result.getName());
    }

    @Test
    public void testGetCandidateById_CandidateDoesNotExist() {
        // Arrange
        when(candidateRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Candidate result = candidateService.getCandidateById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddCandidate() {
        // Arrange
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("New Candidate");

        when(candidateRepository.save(candidate)).thenReturn(candidate);

        // Act
        Candidate result = candidateService.addCandidate(candidate);

        // Assert
        assertNotNull(result);
        assertEquals("New Candidate", result.getName());
        verify(candidateRepository, times(1)).save(candidate);
    }

    @Test
    public void testDeleteCandidateById_CandidateExists() {
        // Arrange
        Long candidateId = 1L;
        when(candidateRepository.existsById(candidateId)).thenReturn(true);

        // Act
        boolean result = candidateService.deleteCandidateById(candidateId);

        // Assert
        assertTrue(result);
        verify(candidateRepository, times(1)).deleteById(candidateId);
    }

    @Test
    public void testDeleteCandidateById_CandidateDoesNotExist() {
        // Arrange
        Long candidateId = 1L;
        when(candidateRepository.existsById(candidateId)).thenReturn(false);

        // Act
        boolean result = candidateService.deleteCandidateById(candidateId);

        // Assert
        assertFalse(result);
        verify(candidateRepository, never()).deleteById(candidateId);
    }
}

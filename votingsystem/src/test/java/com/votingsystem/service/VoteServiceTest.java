package com.votingsystem.service;
import com.votingsystem.service.VoteService;
import com.votingsystem.service.EmailService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.votingsystem.entity.User;
import com.votingsystem.entity.Vote;
import com.votingsystem.repository.UserRepository;
import com.votingsystem.repository.VoteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrUpdateVote_NewVote() {
        // Arrange
        Vote vote = new Vote();
        vote.setEmail("test@example.com");
        vote.setCandidateId(1L);

        User user = new User();
        user.setUserId(100L);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setProfession("Developer");
        user.setGender("Male");
        user.setAge(30);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(voteRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        String result = voteService.saveOrUpdateVote(vote);

        // Assert
        assertEquals("Vote saved successfully.", result);
        verify(voteRepository, times(1)).save(vote);
        verify(emailService, times(1)).sendConfirmationEmail(
                eq("test@example.com"),
                eq("Vote Preference Confirmation"),
                contains("Thank you for voting")
        );
    }

    @Test
    public void testSaveOrUpdateVote_UpdateExistingVote() {
        // Arrange
        Vote vote = new Vote();
        vote.setEmail("test@example.com");
        vote.setCandidateId(2L);

        User user = new User();
        user.setUserId(100L);
        user.setEmail("test@example.com");
        user.setName("Test User");

        Vote existingVote = new Vote();
        existingVote.setCandidateId(1L);
        existingVote.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(voteRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingVote));

        // Act
        String result = voteService.saveOrUpdateVote(vote);

        // Assert
        assertEquals("Vote updated successfully.", result);
        verify(voteRepository, times(1)).save(existingVote);
        verify(emailService, times(1)).sendConfirmationEmail(
                eq("test@example.com"),
                eq("Vote Preference Updated"),
                contains("Your vote preference has been successfully updated")
        );
    }

    @Test
    public void testSaveOrUpdateVote_UserNotRegistered() {
        // Arrange
        Vote vote = new Vote();
        vote.setEmail("unknown@example.com");
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act
        String result = voteService.saveOrUpdateVote(vote);

        // Assert
        assertEquals("Not a registered user.", result);
        verify(voteRepository, never()).save(any());
        verify(emailService, never()).sendConfirmationEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void testWithdrawVote_VoteExists() {
        // Arrange
        String email = "test@example.com";
        Vote existingVote = new Vote();
        existingVote.setEmail(email);

        when(voteRepository.findByEmail(email)).thenReturn(Optional.of(existingVote));

        // Act
        String result = voteService.withdrawVote(email);

        // Assert
        assertEquals("Vote withdrawn successfully.", result);
        verify(voteRepository, times(1)).delete(existingVote);
        verify(emailService, times(1)).sendConfirmationEmail(
                eq(email),
                eq("Vote Withdrawn"),
                contains("Your vote has been successfully withdrawn")
        );
    }

    @Test
    public void testWithdrawVote_VoteDoesNotExist() {
        // Arrange
        String email = "test@example.com";
        when(voteRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        String result = voteService.withdrawVote(email);

        // Assert
        assertEquals("No vote found for this email.", result);
        verify(voteRepository, never()).delete(any());
        verify(emailService, never()).sendConfirmationEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void testGetVotesByProfession() {
        // Arrange
        Vote vote1 = new Vote();
        vote1.setProfession("Developer");
        vote1.setCandidateId(1L);

        Vote vote2 = new Vote();
        vote2.setProfession("Developer");
        vote2.setCandidateId(1L);

        Vote vote3 = new Vote();
        vote3.setProfession("Designer");
        vote3.setCandidateId(2L);

        when(voteRepository.findAll()).thenReturn(Arrays.asList(vote1, vote2, vote3));

        // Act
        Map<String, Map<Long, Integer>> result = voteService.getVotesByProfession();

        // Assert
        assertEquals(2, result.get("Developer").get(1L));
        assertEquals(1, result.get("Designer").get(2L));
    }

    @Test
    public void testGetVotesByAge() {
        // Arrange
        Vote vote1 = new Vote();
        vote1.setAge(30);
        vote1.setCandidateId(1L);

        Vote vote2 = new Vote();
        vote2.setAge(30);
        vote2.setCandidateId(2L);

        Vote vote3 = new Vote();
        vote3.setAge(25);
        vote3.setCandidateId(1L);

        when(voteRepository.findAll()).thenReturn(Arrays.asList(vote1, vote2, vote3));

        // Act
        Map<Integer, Map<Long, Integer>> result = voteService.getVotesByAge();

        // Assert
        assertEquals(1, result.get(30).get(1L));
        assertEquals(1, result.get(30).get(2L));
        assertEquals(1, result.get(25).get(1L));
    }

    @Test
    public void testGetVotesByGender() {
        // Arrange
        Vote vote1 = new Vote();
        vote1.setGender("Male");
        vote1.setCandidateId(1L);

        Vote vote2 = new Vote();
        vote2.setGender("Female");
        vote2.setCandidateId(2L);

        Vote vote3 = new Vote();
        vote3.setGender("Male");
        vote3.setCandidateId(1L);

        when(voteRepository.findAll()).thenReturn(Arrays.asList(vote1, vote2, vote3));

        // Act
        Map<String, Map<Long, Integer>> result = voteService.getVotesByGender();

        // Assert
        assertEquals(2, result.get("Male").get(1L));
        assertEquals(1, result.get("Female").get(2L));
    }
}

package com.votingsystem.controller;

import com.votingsystem.entity.Vote;
import com.votingsystem.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/votes")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class VoteController {

    @Autowired
    private VoteService voteService;

    // Cast or update vote
    @PostMapping
    public ResponseEntity<String> castOrUpdateVote(@RequestParam String email, @RequestParam Long candidateId) {
        Vote vote = new Vote();
        vote.setCandidateId(candidateId);
        vote.setEmail(email);

        String responseMessage = voteService.saveOrUpdateVote(vote);
        return ResponseEntity.ok(responseMessage);
    }

    // Withdraw vote
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdrawVote(@RequestParam String email) {
        String responseMessage = voteService.withdrawVote(email);
        return ResponseEntity.ok(responseMessage);
    }

    // Get votes categorized by profession
    @GetMapping("/profession")
    public ResponseEntity<Map<String, Map<Long, Integer>>> getVotesByProfession() {
        Map<String, Map<Long, Integer>> votesByProfession = voteService.getVotesByProfession();
        return ResponseEntity.ok(votesByProfession);
    }

    // Get votes categorized by age
    @GetMapping("/age")
    public ResponseEntity<Map<Integer, Map<Long, Integer>>> getVotesByAge() {
        Map<Integer, Map<Long, Integer>> votesByAge = voteService.getVotesByAge();
        return ResponseEntity.ok(votesByAge);
    }

    // Get votes categorized by gender
    @GetMapping("/gender")
    public ResponseEntity<Map<String, Map<Long, Integer>>> getVotesByGender() {
        Map<String, Map<Long, Integer>> votesByGender = voteService.getVotesByGender();
        return ResponseEntity.ok(votesByGender);
    }
}

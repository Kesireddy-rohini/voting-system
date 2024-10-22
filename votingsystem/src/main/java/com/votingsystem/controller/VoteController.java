package com.votingsystem.controller;

import com.votingsystem.entity.Vote;
import com.votingsystem.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

   @PostMapping
    public ResponseEntity<String> castVote(@RequestParam String email, @RequestParam Long candidateId) {
        // Create a Vote object with only email and candidateId
        Vote vote = new Vote();
        vote.setCandidateId(candidateId);
        vote.setEmail(email);

        String responseMessage = voteService.saveVote(vote);
        return ResponseEntity.ok(responseMessage);
    }

   @GetMapping("/profession")
   public ResponseEntity<Map<String, Map<Long, Integer>>> getVotesByProfession() {
       Map<String, Map<Long, Integer>> votesByProfession = voteService.getVotesByProfession();
       return ResponseEntity.ok(votesByProfession);
   }

}

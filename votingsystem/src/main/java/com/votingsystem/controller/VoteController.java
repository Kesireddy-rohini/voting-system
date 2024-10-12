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
    public ResponseEntity<Void> castVote(@RequestBody Vote vote) {
        voteService.saveVote(vote);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/results")
    public ResponseEntity<Map<String, Map<Long, Integer>>> getVoteResults() {
        Map<String, Map<Long, Integer>> results = voteService.getVotesByProfession();
        return ResponseEntity.ok(results);
    }
}

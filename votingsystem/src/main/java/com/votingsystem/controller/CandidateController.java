package com.votingsystem.controller;

import com.votingsystem.entity.Candidate;
import com.votingsystem.entity.User;
import com.votingsystem.service.CandidateService;
import com.votingsystem.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    
    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Candidate candidate = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidate);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addCandidate(@Valid @RequestBody Candidate candidate) {
        candidateService.addCandidate(candidate);
        return ResponseEntity.ok("Candidate added successfully");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) {
        boolean isDeleted = candidateService.deleteCandidateById(id);
        
        if (isDeleted) {
            return ResponseEntity.ok("Candidate deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Candidate not found.");
        }
    }

}

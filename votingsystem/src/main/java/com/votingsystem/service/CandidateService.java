package com.votingsystem.service;

import com.votingsystem.entity.Candidate;
import com.votingsystem.entity.User;
import com.votingsystem.entity.Vote;
import com.votingsystem.repository.CandidateRepository;
import com.votingsystem.repository.VoteRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    
	/*
	 * @Autowired private VoteRepository voteRepository;
	 */

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

	public Candidate addCandidate(@Valid Candidate candidate) {
		 return candidateRepository.save(candidate);
		
	}
	public boolean deleteCandidateById(Long id) {
        if (candidateRepository.existsById(id)) {  // Check if the candidate exists
            candidateRepository.deleteById(id);   // Delete the candidate by ID
            return true;
        } else {
            return false;  // Candidate does not exist
        }
    }

}

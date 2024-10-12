package com.votingsystem.service;

import com.votingsystem.entity.Vote;
import com.votingsystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    // Save the vote
    public void saveVote(Vote vote) {
        voteRepository.save(vote);
    }

    // Count votes by profession
    public Map<String, Map<Long, Integer>> getVotesByProfession() {
        List<Vote> votes = voteRepository.findAll();
        Map<String, Map<Long, Integer>> results = new HashMap<>();

        for (Vote vote : votes) {
            String profession = vote.getProfession();
            Long candidateId = vote.getCandidateId(); // Use candidateId directly

            results.putIfAbsent(profession, new HashMap<>());
            results.get(profession).put(candidateId, results.get(profession).getOrDefault(candidateId, 0) + 1);
        }

        return results;
    }
}

package com.votingsystem.service;

import com.votingsystem.entity.User;
import com.votingsystem.entity.Vote;
import com.votingsystem.repository.UserRepository;
import com.votingsystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;

    // Save the vote
    public String saveVote(Vote vote) {
        Optional<User> validUser = userRepository.findByEmail(vote.getEmail());

        if (validUser.isPresent()) {
            // Check if the user has already voted
            if (voteRepository.findByEmail(vote.getEmail()).isPresent()) {
                return "User has already voted.";
            }

            // Save the vote with the user's profession
            User user = validUser.get();
            vote.setUserId(user.getUserId());
            vote.setProfession(validUser.get().getProfession());
            vote.setName(user.getName());
            vote.setGender(user.getGender());
            vote.setAge(user.getAge());
           
            voteRepository.save(vote);
            
            String subject = "Vote Preference Confirmation";
            String body = "Dear " + validUser.get().getName() + ",\n\n" +
                          "Thank you for sharing your opinion! Your preference for candidate ID: " + vote.getCandidateId() + " has been recorded.\n\n" +
                          "We appreciate your feedback and value your participation.\n\n" +
                          "Best regards,\nVoting System Team";
            emailService.sendConfirmationEmail(vote.getEmail(), subject, body);

            return "Vote saved successfully.";
        } else {
            return "Not a registered user.";
        }
    }
    
    public Map<String, Map<Long, Integer>> getVotesByProfession() {
        List<Vote> votes = voteRepository.findAll();
        Map<String, Map<Long, Integer>> result = new HashMap<>();

        for (Vote vote : votes) {
            result
                .computeIfAbsent(vote.getProfession(), k -> new HashMap<>())
                .merge(vote.getCandidateId(), 1, Integer::sum);
        }

        return result;
    }
}
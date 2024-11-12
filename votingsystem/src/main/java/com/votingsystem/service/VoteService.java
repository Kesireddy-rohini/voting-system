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

    // Save or update the vote
    public String saveOrUpdateVote(Vote vote) {
        Optional<User> validUser = userRepository.findByEmail(vote.getEmail());

        if (validUser.isPresent()) {
            // Check if the user has already voted
            Optional<Vote> existingVote = voteRepository.findByEmail(vote.getEmail());

            if (existingVote.isPresent()) {
                // Update the existing vote
                Vote currentVote = existingVote.get();
                currentVote.setCandidateId(vote.getCandidateId());
                voteRepository.save(currentVote);

                // Send email to confirm vote update
                String subject = "Vote Preference Updated";
                String body = "Dear " + validUser.get().getName() + ",\n\n" +
                              "Your vote preference has been successfully updated to candidate ID: " + vote.getCandidateId() + ".\n\n" +
                              "Best regards,\nVoting System Team";
                emailService.sendConfirmationEmail(vote.getEmail(), subject, body);

                return "Vote updated successfully.";
            } else {
                // Save a new vote
                User user = validUser.get();
                vote.setUserId(user.getUserId());
                vote.setProfession(user.getProfession());
                vote.setName(user.getName());
                vote.setGender(user.getGender());
                vote.setAge(user.getAge());

                voteRepository.save(vote);

                // Send confirmation email for the new vote
                String subject = "Vote Preference Confirmation";
                String body = "Dear " + validUser.get().getName() + ",\n\n" +
                              "Thank you for voting! Your preference for candidate ID: " + vote.getCandidateId() + " has been recorded.\n\n" +
                              "Best regards,\nVoting System Team";
                emailService.sendConfirmationEmail(vote.getEmail(), subject, body);

                return "Vote saved successfully.";
            }
        } else {
            return "Not a registered user.";
        }
    }

    // Withdraw the vote
    public String withdrawVote(String email) {
        Optional<Vote> existingVote = voteRepository.findByEmail(email);

        if (existingVote.isPresent()) {
            voteRepository.delete(existingVote.get());

            // Send confirmation email for vote withdrawal
            String subject = "Vote Withdrawn";
            String body = "Dear Voter,\n\nYour vote has been successfully withdrawn. You may cast a new vote anytime before the deadline.\n\nBest regards,\nVoting System Team";
            emailService.sendConfirmationEmail(email, subject, body);

            return "Vote withdrawn successfully.";
        } else {
            return "No vote found for this email.";
        }
    }

    // Categorize votes by profession
    public Map<String, Map<Long, Integer>> getVotesByProfession() {
        List<Vote> votes = voteRepository.findAll();
        Map<String, Map<Long, Integer>> votesByProfession = new HashMap<>();

        for (Vote vote : votes) {
            votesByProfession
                .computeIfAbsent(vote.getProfession(), k -> new HashMap<>())
                .merge(vote.getCandidateId(), 1, Integer::sum);
        }

        return votesByProfession;
    }

    // Categorize votes by age
    public Map<Integer, Map<Long, Integer>> getVotesByAge() {
        List<Vote> votes = voteRepository.findAll();
        Map<Integer, Map<Long, Integer>> votesByAge = new HashMap<>();

        for (Vote vote : votes) {
            votesByAge
                .computeIfAbsent(vote.getAge(), k -> new HashMap<>())
                .merge(vote.getCandidateId(), 1, Integer::sum);
        }

        return votesByAge;
    }

    // Categorize votes by gender
    public Map<String, Map<Long, Integer>> getVotesByGender() {
        List<Vote> votes = voteRepository.findAll();
        Map<String, Map<Long, Integer>> votesByGender = new HashMap<>();

        for (Vote vote : votes) {
            votesByGender
                .computeIfAbsent(vote.getGender(), k -> new HashMap<>())
                .merge(vote.getCandidateId(), 1, Integer::sum);
        }

        return votesByGender;
    }
}

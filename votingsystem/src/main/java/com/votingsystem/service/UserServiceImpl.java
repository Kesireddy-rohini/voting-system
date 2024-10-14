package com.votingsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.votingsystem.entity.User;
import com.votingsystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Register user
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // Send confirmation email
        String subject = "Registration Confirmation";
        String body = "Dear " + user.getName() + ",\n\nThank you for registering to our voting system. Your registration was successful!\n\nBest Regards,\nVoting System Team";
        emailService.sendConfirmationEmail(user.getEmail(), subject, body);

        return savedUser;
    }

    // Login user
    @Override
    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

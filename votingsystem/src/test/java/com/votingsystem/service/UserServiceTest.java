package com.votingsystem.service;


import com.votingsystem.service.EmailService;
import com.votingsystem.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.votingsystem.entity.User;
import com.votingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        User savedUser = new User();
        savedUser.setUserId(12345L);
        savedUser.setName("Test User");
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.registerUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(12345L, result.getUserId());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendConfirmationEmail(
                eq("test@example.com"),
                eq("Registration Confirmation"),
                contains("Thank you for registering to our voting system")
        );
    }

    @Test
    public void testLoginUser_SuccessfulLogin() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        // Act
        Optional<User> result = userService.loginUser(email, password);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testLoginUser_InvalidPassword() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongPassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(false);

        // Act
        Optional<User> result = userService.loginUser(email, password);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        // Arrange
        String email = "unknown@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.loginUser(email, password);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    public void testCheckIfEmailExists_EmailExists() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // Act
        boolean result = userService.checkIfEmailExists(email);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testCheckIfEmailExists_EmailDoesNotExist() {
        // Arrange
        String email = "unknown@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = userService.checkIfEmailExists(email);

        // Assert
        assertFalse(result);
    }
}

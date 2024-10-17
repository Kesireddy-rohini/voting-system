package com.votingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votingsystem.entity.User;
import com.votingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTesting {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Test valid user registration
    @Test
    public void testRegisterUser_ValidInput() throws Exception {
        User user = new User();
        user.setName("Valid User");
        user.setAge(30);
        user.setGender("Male");
        user.setPhonenumber("1234567890");
        user.setProfession("Engineer");
        user.setEmail("validuser@example.com");
        user.setPassword("password1");

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully."));
    }

    // Test invalid email format
    @Test
    public void testRegisterUser_InvalidEmail() throws Exception {
        User user = new User();
        user.setName("Invalid Email User");
        user.setAge(30);
        user.setGender("Female");
        user.setPhonenumber("1234567890");
        user.setProfession("Doctor");
        user.setEmail("invalid-email");  // Invalid email format
        user.setPassword("password1");

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Please provide a valid email address"));
    }

    // Test invalid age (negative or above 150)
    @Test
    public void testRegisterUser_InvalidAge() throws Exception {
        User user = new User();
        user.setName("Invalid Age User");
        user.setAge(200);  // Invalid age
        user.setGender("Male");
        user.setPhonenumber("1234567890");
        user.setProfession("Scientist");
        user.setEmail("ageinvalid@example.com");
        user.setPassword("password1");

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Please provide a valid age"));
    }

    // Test invalid phone number (less or more than 10 digits)
    @Test
    public void testRegisterUser_InvalidPhoneNumber() throws Exception {
        User user = new User();
        user.setName("Invalid Phone User");
        user.setAge(25);
        user.setGender("Female");
        user.setPhonenumber("12345");  // Invalid phone number
        user.setProfession("Artist");
        user.setEmail("invalidphone@example.com");
        user.setPassword("password1");

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Phone number must be 10 digits"));
    }

    // Test invalid password (does not meet strength criteria)
    @Test
    public void testRegisterUser_InvalidPassword() throws Exception {
        User user = new User();
        user.setName("Invalid Password User");
        user.setAge(28);
        user.setGender("Male");
        user.setPhonenumber("9876543210");
        user.setProfession("Engineer");
        user.setEmail("invalidpassword@example.com");
        user.setPassword("pass");  // Invalid password (too short)

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Password must be at least 8 characters long and contain a mix of letters and numbers"));
    }

    // Test duplicate email registration
    @Test
    public void testRegisterUser_DuplicateEmail() throws Exception {
        User user = new User();
        user.setName("Duplicate Email User");
        user.setAge(35);
        user.setGender("Male");
        user.setPhonenumber("0987654321");
        user.setProfession("Teacher");
        user.setEmail("duplicateemail@example.com");
        user.setPassword("password123");

        // Save the user first
        userRepository.save(user);

        // Attempt to register again with the same email
        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already registered."));
    }
}

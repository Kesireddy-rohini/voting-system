package com.votingsystem.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.votingsystem.entity.User;
import com.votingsystem.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	 @PostMapping("/register")
	    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
	        userService.registerUser(user);
	        return ResponseEntity.ok("User registered successfully");
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
	        Optional<User> user = userService.loginUser(email, password);
	        if (user.isPresent()) {
	            return ResponseEntity.ok("User logged in successfully");
	        }
	        return ResponseEntity.status(401).body("Invalid email or password");
	    }
	    
	    @ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
			StringBuilder errors = new StringBuilder();
			for (FieldError error : ex.getBindingResult().getFieldErrors()) {
				errors.append(error.getDefaultMessage()).append("; ");
			}
			return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
		}
	
	
}

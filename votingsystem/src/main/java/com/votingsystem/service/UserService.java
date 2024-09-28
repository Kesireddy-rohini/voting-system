package com.votingsystem.service;

import java.util.Optional;

import com.votingsystem.entity.User;

public interface UserService {
	
	public User registerUser(User user);
	
	public Optional<User> loginUser(String email, String password);

}

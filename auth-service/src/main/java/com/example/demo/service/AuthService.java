package com.example.demo.service;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserCredentialRepository;
import com.example.demo.utility.Roles;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
public class AuthService {

	private final UserCredentialRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public Users registerUser(Users users) {
		Users user = new Users();
		user.setEmail(users.getEmail());
		user.setUsername(users.getUsername());
		user.setRole(users.getRole());
		user.setPassword(passwordEncoder.encode(users.getPassword()));
		return repository.save(user);
	}

	public String generateToken(String username, Roles role) {
		return jwtUtil.generateToken(username, role);
	}

	public String validateToken(String token) {
		if (!jwtUtil.validateToken(token)) {
			return "Token is invalid or expired";
		}
		return "valid token";
	}

	public String login(String email, String password) {
		Users user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}
		String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

		return token;
	}
}
package com.example.demo.service;

import java.util.UUID;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDto;
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

	public void validateToken(String token) {
		if (!jwtUtil.validateToken(token)) {
			throw new RuntimeException("Token is invalid or expired");
		}
<<<<<<< Updated upstream
	}

	public String login(String email, String password) {
		Users user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
=======
		return jwtUtil.validateToken(token) ? "valid token" : "invalid token";
	}

	public String login(String email, String password) {
		Users user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not Found"));
>>>>>>> Stashed changes
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}
		String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

		return token;
	}
	@Transactional
	public UserDto updateStatus(UUID userId) {
		Users user = repository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found : " + userId));
		user.setIs_active(!user.getIs_active());
		Users updatedUser = repository.save(user);
		return UserDto.builder()
				.status(updatedUser.getIs_active())
				.userId(updatedUser.getId())
				.role(updatedUser.getRole())
				.username(updatedUser.getUsername())
				.build();
	}
}
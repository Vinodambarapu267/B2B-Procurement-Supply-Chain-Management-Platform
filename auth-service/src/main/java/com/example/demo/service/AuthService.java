package com.example.demo.service;

import java.util.UUID;

import org.apache.kafka.common.message.ApiVersionsResponseData.FinalizedFeatureKey;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Users;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.exceptions.InvalidTokenException;
import com.example.demo.exceptions.UserAlreadyExistException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.kafka.EventType;
import com.example.demo.kafka.KafkaUserProducer;
import com.example.demo.kafka.UserEvent;
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
	private final KafkaUserProducer kafkaUserProducer;

	public Users registerUser(Users users) {
		repository.findByEmail(users.getEmail()).ifPresent(user -> {
			throw new UserAlreadyExistException("User Already Exist :" + users.getEmail());
		});
		Users user = new Users();
		user.setEmail(users.getEmail());
		user.setUsername(users.getUsername());
		user.setRole(users.getRole());
		user.setPassword(passwordEncoder.encode(users.getPassword()));

		Users save = repository.save(user);
		UserEvent userEvent = UserEvent.builder().userId(save.getId()).username(save.getUsername())
				.isActive(save.getIs_active()).role(save.getRole()).eventType(EventType.REGISTERED).build();
		kafkaUserProducer.publishUserRegistered(userEvent);
		return save;
	}

	public String generateToken(String username, Roles role) {
		return jwtUtil.generateToken(username, role);
	}

	public String validateToken(String token) {
		if (!jwtUtil.validateToken(token)) {
			throw new InvalidTokenException("Token is invalid or expired");
		}
		return token;
	}

	public String login(String email, String password) {
		Users user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not Found"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidPasswordException("Invalid password");
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
		return UserDto.builder().status(updatedUser.getIs_active()).userId(updatedUser.getId())
				.role(updatedUser.getRole()).username(updatedUser.getUsername()).build();
	}
}
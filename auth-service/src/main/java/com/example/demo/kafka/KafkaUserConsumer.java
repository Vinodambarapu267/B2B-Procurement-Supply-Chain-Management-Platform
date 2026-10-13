package com.example.demo.kafka;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Users;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserCredentialRepository;

@Component
public class KafkaUserConsumer {
	private final UserCredentialRepository credentialRepository;

	public KafkaUserConsumer(UserCredentialRepository credentialRepository) {
		super();
		this.credentialRepository = credentialRepository;
	}

	@KafkaListener(topics = { "user-deleted", "user-role-updated", "user-updated" }, groupId = "user-service-group")
	public void handleUSerConsumerEvents(UserEvent event) {
		if (event == null || event.getEventType() == null) {
			throw new RuntimeException("User Event or EventType is null");
		}
		switch (event.getEventType()) {
		case UPDATE_ROLE -> handleUpdateRole(event);
		case UPDATED -> handleUpdateUser(event);
		case DELETED -> handleUserDelete(event);
		default -> throw new IllegalArgumentException("Unsupported event type: " + event.getEventType());
		}

	}

	public void handleUserDelete(UserEvent event) {
		Users user = findUser(event.getUserId());
		credentialRepository.delete(user);
	}

	public void handleUpdateRole(UserEvent event) {
		Users user = findUser(event.getUserId());
		user.setId(event.getUserId());
		user.setRole(event.getRole());
		credentialRepository.save(user);
	}

	public void handleUpdateUser(UserEvent event) {
		Users user = findUser(event.getUserId());
		user.setId(event.getUserId());
		user.setUsername(event.getUsername());
		user.setRole(event.getRole());
		credentialRepository.save(user);
	}

	public Users findUser(UUID id) {
		Users user = credentialRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not Found : " + id));
		return user;
	}
}

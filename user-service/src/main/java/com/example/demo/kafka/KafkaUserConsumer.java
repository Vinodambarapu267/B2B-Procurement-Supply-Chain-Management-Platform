package com.example.demo.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserProfiles;
import com.example.demo.repository.UserProfilesRepository;

@Component
public class KafkaUserConsumer {
	private final UserProfilesRepository repository;

	public KafkaUserConsumer(UserProfilesRepository repository) {
		super();
		this.repository = repository;
	}

	@KafkaListener(topics = { "user-registered" }, groupId = "auth-service-group")
	public void handleUserConsumerEvent(UserEvent event) {
		if (event == null) {
			throw new RuntimeException("UserEvent is null");
		}
		if (event.getEventType() == null) {
			throw new RuntimeException("Event type is null");
		}

		if (EventType.REGISTERED.equals(event.getEventType())) {
			UserProfiles userProfiles = new UserProfiles();
			userProfiles.setId(event.getUserId());
			userProfiles.setCreatedAt(LocalDateTime.now());
			userProfiles.setName(event.getUsername());
			userProfiles.setIsActive(event.getIsActive());
			userProfiles.setRole(event.getRole());
			repository.save(userProfiles);
		}
	}
}

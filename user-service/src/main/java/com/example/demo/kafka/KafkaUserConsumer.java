package com.example.demo.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserProfiles;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserProfilesRepository;

@Component
@Transactional
public class KafkaUserConsumer {
	private final UserProfilesRepository repository;

	public KafkaUserConsumer(UserProfilesRepository repository) {
		super();
		this.repository = repository;
	}

	@KafkaListener(topics = { "user-registered","user-status-changed" }, groupId = "auth-service-group")
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
		
		if(EventType.STATUS_CHANGE.equals(event.getEventType())) {
			UserProfiles user = repository.findById(event.getUserId()).orElseThrow(()-> new UserNotFoundException("User not found : "+event.getUserId()));
			user.setUpdatedAt(LocalDateTime.now());
			user.setIsActive(event.getIsActive());
			repository.save(user);
			System.err.println(event.getIsActive().equals(user.getIsActive()));
		}
	}
}

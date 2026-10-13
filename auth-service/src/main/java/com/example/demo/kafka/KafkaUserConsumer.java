package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserCredentialRepository;

@Component
public class KafkaUserConsumer {
	private final UserCredentialRepository credentialRepository;

	public KafkaUserConsumer(UserCredentialRepository credentialRepository) {
		super();
		this.credentialRepository = credentialRepository;
	}
	
	
	
	@KafkaListener(topics = { "user-deleted" }, groupId = "user-service-group")
	public void handleUSerConsumerEvents(UserEvent event) {
		if (event == null) {
			throw new RuntimeException("Event is null");
		}
		if (event.getEventType() == null) {
			throw new RuntimeException("Event is null");
		}
		if (EventType.DELETED.equals(event.getEventType())) {
			Users user = credentialRepository.findById(event.getUserId())
					.orElseThrow(() -> new RuntimeException("User not Found : " + event.getUserId()));
			credentialRepository.delete(user);
			System.err.println(event.getUserId().equals(user.getId()));
		}
	}
}

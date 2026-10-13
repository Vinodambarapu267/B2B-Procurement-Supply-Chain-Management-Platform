package com.example.demo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserProducer {
	private final KafkaTemplate<String, UserEvent> kafkaTemplate;

	public KafkaUserProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void pulishUserDeleted(UserEvent event) {
		kafkaTemplate.send("user-deleted", event.getUserId().toString(), event);
	}

	public void publishUserRole(UserEvent event) {
		kafkaTemplate.send("user-role-updated", event.getUserId().toString(), event);
	}

	public void publishUserUpdate(UserEvent event) {
		kafkaTemplate.send("user-updated", event.getUserId().toString(), event);
	}
}

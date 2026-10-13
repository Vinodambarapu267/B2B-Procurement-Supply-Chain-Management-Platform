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

	public void publishUserRegistered(UserEvent event) {
		kafkaTemplate.send("user-registered", event.getUserId().toString(), event);
	}
}

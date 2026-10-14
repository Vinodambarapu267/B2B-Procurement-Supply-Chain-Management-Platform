package com.example.demo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPurchaseRequestProducer {
	private final KafkaTemplate<String, PurchaseRequestEvent> kafkaTemplate;

	public KafkaPurchaseRequestProducer(KafkaTemplate<String, PurchaseRequestEvent> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publisPurchaseRequestCreated(PurchaseRequestEvent event) {
		kafkaTemplate.send("purchase-request-created", event.getPrId().toString(), event);
	}
}

package com.vinod.b2b.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPurchaseApprovalProducer {
	private final KafkaTemplate<String, PurchaseRequestDecisionEvent> kafkaTemplate;

	public KafkaPurchaseApprovalProducer(KafkaTemplate<String, PurchaseRequestDecisionEvent> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void handlePurchaseRequestApproval(PurchaseRequestDecisionEvent event) {
		kafkaTemplate.send("purchase-request-approved", event.getPrId().toString(), event);
	}
	public void handlePurchaseRequestReject(PurchaseRequestDecisionEvent event) {
		kafkaTemplate.send("purchase-request-rejected", event.getPrId().toString(), event);
	}
}

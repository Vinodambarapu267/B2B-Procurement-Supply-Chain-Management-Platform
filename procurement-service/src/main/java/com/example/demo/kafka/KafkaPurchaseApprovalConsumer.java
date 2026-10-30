package com.example.demo.kafka;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PurchaseRequest;
import com.example.demo.exception.PurchaseRequestNotFoundException;
import com.example.demo.repository.PurchaseRequestRepository;
import com.example.demo.utility.PurchaseStatus;

@Component
public class KafkaPurchaseApprovalConsumer {
	private final PurchaseRequestRepository purchaseRequestRepository;

	public KafkaPurchaseApprovalConsumer(PurchaseRequestRepository purchaseRequestRepository) {
		super();
		this.purchaseRequestRepository = purchaseRequestRepository;
	}

	@Transactional
	@Modifying
	@KafkaListener(topics = { "purchase-request-approved",
			"purchase-request-rejected" }, groupId = "approvel-service-group")
	public void handlePurchaseApprovalEvent(PurchaseRequestDecisionEvent event) {
		if (event.getEventType() == null) {
			throw new IllegalArgumentException("Event type cannot be null");
		}
		PurchaseStatus status = switch (event.getEventType()) {
		case APPROVED -> PurchaseStatus.APPROVED;
		case REJECTED -> PurchaseStatus.REJECTED;
		default -> throw new IllegalArgumentException("Unsupported event type: " + event.getEventType());
		};

		PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(event.getPrId()).orElseThrow(
				() -> new PurchaseRequestNotFoundException("Purchase Request not found: " + event.getPrId()));
		purchaseRequest.setStatus(status);
		purchaseRequest.setUpdatedAt(LocalDateTime.now());
		purchaseRequestRepository.save(purchaseRequest);
	}

}

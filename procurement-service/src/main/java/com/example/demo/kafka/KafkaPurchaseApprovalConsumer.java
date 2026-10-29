package com.example.demo.kafka;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PurchaseRequest;
import com.example.demo.exception.PurchaseRequestNotFoundException;
import com.example.demo.repository.PurchaseRequestRepository;

@Component
public class KafkaPurchaseApprovalConsumer {
	private final PurchaseRequestRepository purchaseRequestRepository;

	public KafkaPurchaseApprovalConsumer(PurchaseRequestRepository purchaseRequestRepository) {
		super();
		this.purchaseRequestRepository = purchaseRequestRepository;
	}

	@Transactional
	@Modifying
	@KafkaListener(topics = {"purchase-request-approved"},groupId = "approvel-service-group")
	public void handlePurchaseApprovalEvent(PurchaseRequestDecisionEvent event) {
		if (event.getEventType().equals(EventType.APPROVED)) {

			PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(event.getPrId())
					.orElseThrow(() -> new PurchaseRequestNotFoundException("Purchase Request Not found"));
			purchaseRequest.setStatus(event.getDecision());
			purchaseRequest.setUpdatedAt(LocalDateTime.now());
			purchaseRequestRepository.save(purchaseRequest);
		}
	}
}

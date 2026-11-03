package com.example.demo.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.entity.PurchaseRequest;
import com.example.demo.exception.PurchaseRequestNotFoundException;
import com.example.demo.repository.PurchaseRequestRepository;
import com.example.demo.utility.PurchaseStatus;

@Component
public class KafkaPurchaseOrderConsumer {

	private final PurchaseRequestRepository requestRepository;

	public KafkaPurchaseOrderConsumer(PurchaseRequestRepository requestRepository) {
		super();
		this.requestRepository = requestRepository;
	}

	@KafkaListener(topics = { "purchase-order-created" }, groupId = "procurement-service-group")
	public void handlePurchaseOrder(PurchaseRequestEvent event) {
		switch (event.getEventType()) {
		case GENERATED -> handlePurchaseOrderCreated(event);
		}
	}

	public void handlePurchaseOrderCreated(PurchaseRequestEvent event) {
		PurchaseRequest purchaseRequest = requestRepository.findById(event.getPrId()).orElseThrow(
				() -> new PurchaseRequestNotFoundException("Purchase Request not found : " + event.getPrId()));
		purchaseRequest.setStatus(PurchaseStatus.GENERATED);
		purchaseRequest.setVersion(purchaseRequest.getVersion() + 1);
		purchaseRequest.setUpdatedAt(LocalDateTime.now());
		requestRepository.save(purchaseRequest);
	}
}

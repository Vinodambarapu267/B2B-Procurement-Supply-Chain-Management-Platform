package com.vinod.b2b.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class kafkaPurchaseOrderProducer {
	private final KafkaTemplate<String, PurchaseRequestEvent> kafkaTemplate;

	public kafkaPurchaseOrderProducer(KafkaTemplate<String, PurchaseRequestEvent> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void handlePurchaseOrderGenerated(PurchaseRequestEvent event) {
		kafkaTemplate.send("purchase-order-created", event.getPrId().toString(), event);
	}

	public void handlePurchaseOrderSent(PurchaseRequestEvent event) {
		kafkaTemplate.send("purchase-order-sent", event.getPrId().toString(), event);
	}

	public void handlePurchaseOrderAccept(PurchaseRequestEvent event) {
		kafkaTemplate.send("purchase-order-accepted", event.getPrId().toString(), event);
	}
}

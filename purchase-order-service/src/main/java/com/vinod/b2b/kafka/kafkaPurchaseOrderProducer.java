package com.vinod.b2b.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class kafkaPurchaseOrderProducer {
	private final KafkaTemplate<String, PurchaseOrderEvent> kafkaTemplate;

	public kafkaPurchaseOrderProducer(KafkaTemplate<String, PurchaseOrderEvent> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void handlePurchaseOrderGenerated(PurchaseOrderEvent event) {
		kafkaTemplate.send("purchase-order-created", event.getPrId().toString(), event);
	}

	public void handlePurchaseOrderSent(PurchaseOrderEvent event) {
		kafkaTemplate.send("purchase-order-sent", event.getPrId().toString(), event);
	}

	public void handlePurchaseOrderAccept(PurchaseOrderEvent event) {
		kafkaTemplate.send("purchase-order-accepted", event.getPrId().toString(), event);
	}
}

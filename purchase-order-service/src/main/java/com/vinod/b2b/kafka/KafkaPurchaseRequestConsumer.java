package com.vinod.b2b.kafka;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.repository.PurchaseOrderRepository;
import com.vinod.b2b.utility.OrderStatus;
@Component
public class KafkaPurchaseRequestConsumer {

	private final PurchaseOrderRepository orderRepository;

	public KafkaPurchaseRequestConsumer(PurchaseOrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}
	@KafkaListener(topics = { "purchase-request-created" }, groupId = "purchase-order-service-group")
	public void handlePurchaserequestcreated(PurchaseRequestEvent event) {
		if (event.getEventType().equals(EventType.CREATED)) {
			PurchaseOrders orders = new PurchaseOrders();
			orders.setPrId(event.getPrId());
			orders.setItems(event.getItems());
			orders.setPoNumber(generatePONumber());
			orders.setStatus(OrderStatus.CREATED);
			orders.setTotalAmount(event.getTotalEstimation());
			orderRepository.save(orders);

		}
		
	}
	private String generatePONumber() {
		return "PO" + UUID.randomUUID()
							.toString()
							.substring(0, 5)
							.toUpperCase();

	}
}

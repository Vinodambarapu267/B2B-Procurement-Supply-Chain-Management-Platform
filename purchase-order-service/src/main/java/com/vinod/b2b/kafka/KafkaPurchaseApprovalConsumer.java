package com.vinod.b2b.kafka;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.repository.PurchaseOrderRepository;
import com.vinod.b2b.utility.OrderStatus;

@Component
public class KafkaPurchaseApprovalConsumer {
	private final PurchaseOrderRepository orderRepository;

	public KafkaPurchaseApprovalConsumer(PurchaseOrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	@Transactional
	@Modifying
	@KafkaListener(topics = { "purchase-request-approved" }, groupId = "purchase-order-service-group")
	public void handlePurchaseApprovalEvent(PurchaseRequestDecisionEvent event) {
		if (event.getEventType().equals(EventType.APPROVED)) {
			PurchaseOrders orders = new PurchaseOrders();
			orders.setPrId(event.getPrId());
			orders.setPoNumber(generatePONumber());
			orders.setStatus(OrderStatus.APPROVED);
			orders.setTotalAmount(event.getTotalEstimate());
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

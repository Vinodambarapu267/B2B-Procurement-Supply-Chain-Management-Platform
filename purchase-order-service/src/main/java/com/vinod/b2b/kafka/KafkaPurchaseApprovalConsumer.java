package com.vinod.b2b.kafka;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.exceptions.PurchaseOrderNotFoundException;
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
			PurchaseOrders orders = orderRepository.findByPrIdAndStatus(event.getPrId(), OrderStatus.GENERATED).orElseThrow(()-> new PurchaseOrderNotFoundException("Purchase Order Already : "+OrderStatus.APPROVED));
			orders.setPrId(event.getPrId());
			orders.setStatus(OrderStatus.APPROVED);
			orderRepository.save(orders);
		}
	}

	
}
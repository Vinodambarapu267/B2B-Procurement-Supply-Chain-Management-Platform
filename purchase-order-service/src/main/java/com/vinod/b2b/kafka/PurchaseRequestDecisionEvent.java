package com.vinod.b2b.kafka;

import java.util.UUID;

import com.vinod.b2b.utility.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDecisionEvent {
	private UUID prId;
	private Double totalEstimate;
	private OrderStatus decision;
	private EventType eventType;
}

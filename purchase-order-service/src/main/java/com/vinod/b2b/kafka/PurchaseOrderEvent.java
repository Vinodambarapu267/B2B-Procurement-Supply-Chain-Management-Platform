package com.vinod.b2b.kafka;

import java.util.List;
import java.util.UUID;

import com.vinod.b2b.dto.PurchaseItem;
import com.vinod.b2b.utility.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderEvent {
	private UUID purchaseOrderId;
	private UUID prId;
	private List<PurchaseItem> items;
	private OrderStatus status;
	private EventType eventType;

}
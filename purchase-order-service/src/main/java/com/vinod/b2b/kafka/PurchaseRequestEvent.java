package com.vinod.b2b.kafka;

import java.time.LocalDateTime;
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
public class PurchaseRequestEvent {
	private UUID prId;
	private UUID requesterId;
	private Double totalEstimation;
	private OrderStatus status;
	private Integer version;
	private List<PurchaseItem> items;
	private LocalDateTime submittedAt;
	private EventType eventType;
}

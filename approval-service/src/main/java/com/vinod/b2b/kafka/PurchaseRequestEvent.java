package com.vinod.b2b.kafka;

import java.time.LocalDateTime;
import java.util.UUID;

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
	private Integer version;
	private LocalDateTime submittedAt;
	private EventType eventType;
}

package com.example.demo.kafka;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.utility.PurchaseStatus;

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
	private PurchaseStatus status;
	private Integer version;
	private LocalDateTime submittedAt;
	private EventType eventType;
}

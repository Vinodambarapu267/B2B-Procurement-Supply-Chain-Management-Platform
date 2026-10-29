package com.example.demo.kafka;

import java.util.UUID;

import com.example.demo.utility.ApprovalLevel;
import com.example.demo.utility.PurchaseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDecisionEvent {
	private UUID prId;
	private UUID requesterId;
	private Double totalEstimate;
	private PurchaseStatus decision;
	private ApprovalLevel approvalLevel;
	private String reason;
	private EventType eventType;
}

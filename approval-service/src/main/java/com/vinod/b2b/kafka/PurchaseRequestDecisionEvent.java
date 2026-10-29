package com.vinod.b2b.kafka;

import java.util.UUID;

import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

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
	private Decision decision;
	private ApprovalLevel approvalLevel;
	private String reason;
	private EventType eventType;
}

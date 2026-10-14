package com.vinod.b2b.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.repository.ApprovalRepository;
import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

@Component
public class KafkaPurchaseRequestConsumer {
	private final ApprovalRepository approvalRepository;

	public KafkaPurchaseRequestConsumer(ApprovalRepository approvalRepository) {
		super();
		this.approvalRepository = approvalRepository;
	}

	@KafkaListener(topics = { "purchase-request-created" }, groupId = "purchase-request-group")
	public void handlePurchaseRequest(PurchaseRequestEvent event) {
		if (event == null || event.getEventType() == null) {
			throw new IllegalArgumentException("PurchaseRequest event or Event Type is null");
		}
		if (EventType.CREATED.equals(event.getEventType())) {
			ApprovalDecisions approvalDecisions = new ApprovalDecisions();
			approvalDecisions.setDecidedAt(LocalDateTime.now());
			approvalDecisions.setDecision(Decision.PENDING_APPROVAL);
			approvalDecisions.setPrId(event.getPrId());
			approvalDecisions.setReason("Awaiting manager approval");
			approvalDecisions.setLevel(ApprovalLevel.MANAGER);
			approvalRepository.save(approvalDecisions);
		}
	}
}

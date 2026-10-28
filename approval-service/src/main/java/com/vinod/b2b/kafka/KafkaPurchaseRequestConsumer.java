package com.vinod.b2b.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.entity.ApprovalWorkflow;
import com.vinod.b2b.repository.ApprovalRepository;
import com.vinod.b2b.repository.ApprovalWorkFlowRepository;
import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

@Component
public class KafkaPurchaseRequestConsumer {
	;
	private final ApprovalRepository approvalRepository;
	private final ApprovalWorkFlowRepository approvalWorkFlowRepository;

	public KafkaPurchaseRequestConsumer(ApprovalRepository approvalRepository,
			ApprovalWorkFlowRepository approvalWorkFlowRepository) {
		super();
		this.approvalRepository = approvalRepository;
		this.approvalWorkFlowRepository = approvalWorkFlowRepository;

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

			ApprovalWorkflow approvalWorkflow = new ApprovalWorkflow();
			approvalWorkflow.setPrId(event.getPrId());
			approvalWorkflow.setRequesterId(event.getRequesterId());
			approvalWorkflow.setTotalEstimate(event.getTotalEstimation());
			approvalWorkflow.setCurrentLevel(ApprovalLevel.MANAGER);
			approvalWorkflow.setStatus(Decision.PENDING_APPROVAL);

			approvalWorkFlowRepository.save(approvalWorkflow);
		}
	}
}

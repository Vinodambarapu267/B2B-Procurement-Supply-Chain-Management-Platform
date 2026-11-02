package com.vinod.b2b.kafka;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.entity.ApprovalWorkflow;
import com.vinod.b2b.exceptions.PurchaseRequestNotFoundException;
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

	@KafkaListener(topics = { "purchase-request-created",
			"purchase-request-cancelled" }, groupId = "purchase-request-group")
	public void handlePurchaseRequest(PurchaseRequestEvent event) {
		if (event == null || event.getEventType() == null) {
			throw new IllegalArgumentException("PurchaseRequest event or Event Type is null");
		}
		switch (event.getEventType()) {
		case CREATED -> handlePurchaseRequestCreate(event);
		case CANCELLED -> handlePurchaseRequestCancelled(event);
		default -> throw new IllegalArgumentException("Eventype is not supported");
		}
	}

	public void handlePurchaseRequestCreate(PurchaseRequestEvent event) {
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
		approvalWorkflow.setStatus(Decision.PENDING_APPROVAL);

		approvalWorkFlowRepository.save(approvalWorkflow);
	}

	public void handlePurchaseRequestCancelled(PurchaseRequestEvent event) {
		ApprovalDecisions approvalDecisions = approvalRepository.findByPrId(event.getPrId())
				.orElseThrow(() -> new PurchaseRequestNotFoundException("Purchase request Not Found "));
		approvalDecisions.setDecision(Decision.CANCELLED);
		approvalDecisions.setReason("Purchase request is cancelled by Owner or Admin");
		approvalDecisions.setDecidedAt(LocalDateTime.now());
		approvalRepository.save(approvalDecisions);

		ApprovalWorkflow approvalWorkflow = approvalWorkFlowRepository.findByPrId(event.getPrId()).orElseThrow(
				() -> new PurchaseRequestNotFoundException("Purchase request not found " + event.getPrId()));
		approvalWorkflow.setPrId(event.getPrId());
		approvalWorkflow.setStatus(Decision.CANCELLED);
		approvalWorkFlowRepository.save(approvalWorkflow);

	}
}

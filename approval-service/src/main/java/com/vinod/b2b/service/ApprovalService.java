package com.vinod.b2b.service;

import java.util.List;
import java.util.UUID;

import com.vinod.b2b.dto.ApprovalDecisionDto;
import com.vinod.b2b.entity.ApprovalDecisions;

public interface ApprovalService {
	public ApprovalDecisions addApporvalDecision(UUID prId,ApprovalDecisionDto approvalDecisionDto);

	public List<ApprovalDecisions> findAllPendingDecisions(UUID approvalId);

	public List<ApprovalDecisions> getApprovalHistory(UUID prId);
}

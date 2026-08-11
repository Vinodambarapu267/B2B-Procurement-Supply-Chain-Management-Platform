package com.vinod.b2b.service;

import java.util.List;
import java.util.UUID;

import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.utility.Decision;

public interface ApprovalService {
	public ApprovalDecisions addApporvalDecision(UUID prId, UUID approvalId, Decision decision);

	public List<ApprovalDecisions> findAllPendingDecisions(UUID approvalId);

	public List<ApprovalDecisions> getApprovalHistory(UUID prId);
}

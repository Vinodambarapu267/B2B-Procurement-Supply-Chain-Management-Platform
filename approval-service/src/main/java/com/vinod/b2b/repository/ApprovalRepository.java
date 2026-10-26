package com.vinod.b2b.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.utility.Decision;

public interface ApprovalRepository extends JpaRepository<ApprovalDecisions, UUID> {
	ApprovalDecisions findByPrId(UUID id);

	List<ApprovalDecisions> findAllByPrId(UUID prId);

	List<ApprovalDecisions> findAllByApproverIdAndDecision(UUID approverId, Decision decision);
}

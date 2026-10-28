package com.vinod.b2b.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinod.b2b.entity.ApprovalDecisions;
import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

public interface ApprovalRepository extends JpaRepository<ApprovalDecisions, UUID> {
	Optional<ApprovalDecisions> findByPrId(UUID id);

	List<ApprovalDecisions> findAllByPrId(UUID prId);

	List<ApprovalDecisions> findAllByDecision(Decision decision);

	boolean existsByPrIdAndDecisionAndLevel(UUID prId, Decision decision, ApprovalLevel level);

	boolean existsByPrIdAndLevel(UUID prId, ApprovalLevel level);

	Optional<ApprovalDecisions> findByPrIdAndLevel(UUID prId, ApprovalLevel level);
}

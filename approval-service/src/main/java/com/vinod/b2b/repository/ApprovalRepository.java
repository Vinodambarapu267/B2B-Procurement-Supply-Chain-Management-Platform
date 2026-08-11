package com.vinod.b2b.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinod.b2b.entity.ApprovalDecisions;
import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApprovalDecisions, UUID> {
	ApprovalDecisions findByPrId(UUID id);

	List<ApprovalDecisions> findAllByPrId(UUID prId);
}

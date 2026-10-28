package com.vinod.b2b.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinod.b2b.entity.ApprovalWorkflow;

public interface ApprovalWorkFlowRepository extends JpaRepository<ApprovalWorkflow, UUID> {

	Optional<ApprovalWorkflow> findByPrId(UUID prId);
}

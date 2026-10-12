package com.vinod.b2b.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vinod.b2b.entity.ApprovalDecisions;

import io.lettuce.core.dynamic.annotation.Param;

public interface ApprovalRepository extends JpaRepository<ApprovalDecisions, UUID> {
	ApprovalDecisions findByPrId(UUID id);

	List<ApprovalDecisions> findAllByPrId(UUID prId);

	@Query("SELECT a FROM ApprovalDecisions a WHERE a.approverId = :approverId")
	List<ApprovalDecisions> findAllByApproverId(@Param("approverId") UUID approverId);
}

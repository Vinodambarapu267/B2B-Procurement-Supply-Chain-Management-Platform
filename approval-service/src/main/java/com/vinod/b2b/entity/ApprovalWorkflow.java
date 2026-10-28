package com.vinod.b2b.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "approval_workflows")
@Data
public class ApprovalWorkflow {
	@Id
	private UUID prId;
	@Column(name = "requester_id", nullable = false)
	private UUID requesterId;
	@Column(nullable = false)
	private Double totalEstimate;
	@Enumerated(EnumType.STRING)
	private ApprovalLevel currentLevel;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Decision status;
	@CreationTimestamp
	@Column(nullable = false)
	private Instant createdAt;
	@UpdateTimestamp
	@Column(nullable = false)
	private Instant updatedAt;
}

package com.vinod.b2b.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.vinod.b2b.utility.ApprovalLevel;
import com.vinod.b2b.utility.Decision;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "approval_decisions")
public class ApprovalDecisions {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(nullable = false)
	private UUID prId;
	@Column(nullable = false)
	private UUID approverId;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ApprovalLevel level;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Decision decision;
	@Column(nullable = false)
	private String reason;
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime decidedAt;
}

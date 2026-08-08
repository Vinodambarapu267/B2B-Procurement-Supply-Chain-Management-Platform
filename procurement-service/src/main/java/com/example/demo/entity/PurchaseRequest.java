package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.example.demo.dto.PurchaseItem;
import com.example.demo.utility.PurchaseStatus;

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
@Table(name = "purchase_requests")
public class PurchaseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID purchaseId;
	@Column(nullable = false)
	private UUID requesterId;
	@Column(columnDefinition = "jsonb", nullable = false)
	@JdbcTypeCode(SqlTypes.JSON)
	private List<PurchaseItem> items;
	@Column(nullable = false)
	private Double totalEstimation;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PurchaseStatus status;
	private Integer version = 0;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}

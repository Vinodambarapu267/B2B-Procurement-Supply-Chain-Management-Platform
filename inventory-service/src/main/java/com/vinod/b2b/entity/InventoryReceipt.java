package com.vinod.b2b.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory_receipts")
public class InventoryReceipt {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private UUID receiptId;
	@Column(nullable = false)
	private UUID poId;
	@ManyToOne
	@JoinColumn(name = "item_id", nullable = false)
	private InventoryItem itemId;
	@Column(nullable = false)
	private Integer quantityExpected;
	@Column(nullable = false)
	private Integer quantityReceived;
	private Boolean discrepancyFlag = false;
	@Column(nullable = false)
	private String receivedBy;
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime receivedAt;
}

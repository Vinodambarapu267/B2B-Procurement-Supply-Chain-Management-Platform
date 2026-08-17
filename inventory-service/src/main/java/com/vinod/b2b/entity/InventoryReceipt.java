package com.vinod.b2b.entity;

import java.util.UUID;

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
	private Integer quantitityReceived;
	private Boolean discrepencyFlag = false;
}

package com.vinod.b2b.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory_items")
public class InventoryItem {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private UUID itemId;
	@Column(nullable = false,unique = true)
	private String sku;
	private Integer quantityOnHand = 0;
	@Column(nullable = false)
	private Integer recordThreshold;
}

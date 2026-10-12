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
@Table(name = "suppliers")
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private UUID supplierId;
	@Column(nullable = false, length = 200)
	private String supplierName;
	@Column(nullable = false, length = 100)
	private String category;
	private Double rating = 0.0;
	private Boolean isActive = true;
}

package com.vinod.b2b.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vinod.b2b.dto.PurchaseItem;
import com.vinod.b2b.utility.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "purchase_orders")
public class PurchaseOrders {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private UUID purchaseOrderId;
	@Column(nullable = false)
	private UUID prId;
	@Column(nullable = true)
	private UUID supplierId;
	@Column(nullable = false, unique = true)
	private String poNumber;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@Column(columnDefinition = "json", nullable = false)
	@JdbcTypeCode(SqlTypes.JSON)
	private List<PurchaseItem> items;
	@Column(nullable = false)
	private Double totalAmount;
	@Column(nullable = false)
	private Integer version = 0;
}

package com.vinod.b2b.entity;

import java.util.UUID;

import com.vinod.b2b.utility.OrderStatus;

import jakarta.persistence.Entity;
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
	private UUID purchaseOrderId;
	private UUID prId;
	private UUID supplierId;
	private Integer poNumber;
	private OrderStatus status;
	private Double totalAmount;
	private Integer version;
}

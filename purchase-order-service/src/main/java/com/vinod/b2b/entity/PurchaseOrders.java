package com.vinod.b2b.entity;

import java.util.UUID;

import org.aspectj.weaver.ast.Var;
import org.springframework.web.jsf.FacesContextUtils;

import com.vinod.b2b.utility.OrderStatus;

import jakarta.persistence.Column;
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
	@Column(nullable = false)
	private UUID purchaseOrderId;
	@Column(nullable = false)
	private UUID prId;
	@Column(nullable = false)
	private UUID supplierId;
	@Column(nullable = false, unique = true)
	private String poNumber;
	@Column(nullable = false)
	private OrderStatus status;
	@Column(nullable = false)
	private Double totalAmount;
	@Column(nullable = false)
	private Integer version=0;
}

package com.vinod.b2b.dto;

import java.util.UUID;

import com.vinod.b2b.utility.OrderStatus;

import lombok.Data;

@Data
public class PurchaseOrderCreateRequest {
	private UUID prId;
	private String supplierName;
	private OrderStatus status;
}

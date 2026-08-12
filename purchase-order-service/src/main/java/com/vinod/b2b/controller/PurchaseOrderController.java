package com.vinod.b2b.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.service.PurchaseOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
	private final PurchaseOrderService purchaseOrderService;

	@PostMapping
	public PurchaseOrders generatePurchaseOrder(@RequestBody PurchaseOrders purchaseOrders) {
		return purchaseOrderService.generatePurchaseOrder(purchaseOrders);
	}
}

package com.vinod.b2b.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.service.PurchaseOrderService;
import com.vinod.b2b.utility.OrderStatus;

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

	@PatchMapping("/{id}/status")
	public PurchaseOrders updatePurchaseOrderStatus(@PathVariable(name = "id") UUID purchaseOrderId,
			@RequestParam OrderStatus status) {
		return purchaseOrderService.updatePurchaseOrderstatus(purchaseOrderId, status);
	}
	@GetMapping("/{id}")
	public PurchaseOrders findByPurchaseOrders(@PathVariable(name="id") UUID purchaseOrderId) {
		return purchaseOrderService.getPurchaseOrder(purchaseOrderId);
				
	}
}

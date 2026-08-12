package com.vinod.b2b.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.PurchaseOrders;

@Service
public interface PurchaseOrderService {
	PurchaseOrders generatePurchaseOrder(PurchaseOrders purchaseOrders);

	PurchaseOrders updatePurchaseOrderstatus(UUID purchaseOrderId);

	PurchaseOrders getPurchaseOrdeer(UUID purchaseOrderId);
}

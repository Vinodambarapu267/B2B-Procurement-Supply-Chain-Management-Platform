package com.vinod.b2b.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.utility.OrderStatus;

@Service
public interface PurchaseOrderService {
	PurchaseOrders generatePurchaseOrder(PurchaseOrders purchaseOrders);

	PurchaseOrders updatePurchaseOrderstatus(UUID purchaseOrderId,OrderStatus status);

	PurchaseOrders getPurchaseOrdeer(UUID purchaseOrderId);
}

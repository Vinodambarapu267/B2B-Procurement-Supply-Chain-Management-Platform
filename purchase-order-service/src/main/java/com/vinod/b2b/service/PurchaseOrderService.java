package com.vinod.b2b.service;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.utility.OrderStatus;

@Service
public interface PurchaseOrderService {
	PurchaseOrders generatePurchaseOrder(PurchaseOrders purchaseOrders, Authentication authentication);

	PurchaseOrders updatePurchaseOrderstatus(UUID purchaseOrderId, OrderStatus status);

	PurchaseOrders getPurchaseOrder(UUID purchaseOrderId, Authentication authentication);

	PurchaseOrders sendPurchaseOrder(UUID purchaseOrderId);

	PurchaseOrders acceptPurchaseOrder(UUID purchaseOrderId, Authentication authentication);

	PurchaseOrders dispatchPurchaseOrder(UUID purchaseOrderId, Authentication authentication);

	PurchaseOrders completePurchaseOrder(UUID purchaseOrderId);
}

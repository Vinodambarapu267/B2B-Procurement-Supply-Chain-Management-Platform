package com.vinod.b2b.serviceimpl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.repository.PurchaseOrderRepository;
import com.vinod.b2b.service.PurchaseOrderService;
import com.vinod.b2b.utility.OrderStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	private final PurchaseOrderRepository repository;

	@Override
	public PurchaseOrders generatePurchaseOrder(PurchaseOrders purchaseOrders) {
		PurchaseOrders order = new PurchaseOrders();
		order.setPrId(purchaseOrders.getPrId());
		order.setSupplierId(purchaseOrders.getSupplierId());
		order.setPoNumber(generateRandomPONumber());
		order.setStatus(purchaseOrders.getStatus());
		order.setTotalAmount(purchaseOrders.getTotalAmount());
		return repository.save(order);
	}

	@Override
	public PurchaseOrders updatePurchaseOrderstatus(UUID purchaseOrderId,OrderStatus status) {
		PurchaseOrders purchaseOrder = repository.findById(purchaseOrderId).orElseThrow(()-> new RuntimeException("PUrchase Order not found ID : "+purchaseOrderId));
		purchaseOrder.setStatus(status);
		purchaseOrder.setVersion(purchaseOrder.getVersion()+1);
		return repository.save(purchaseOrder);
	}

	@Override
	public PurchaseOrders getPurchaseOrder(UUID purchaseOrderId) {
		return repository.findById(purchaseOrderId).orElseThrow(()-> new RuntimeException("PurchaseOrder not found ID : "+purchaseOrderId));
	}

	private String generateRandomPONumber() {
		return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
	}
}

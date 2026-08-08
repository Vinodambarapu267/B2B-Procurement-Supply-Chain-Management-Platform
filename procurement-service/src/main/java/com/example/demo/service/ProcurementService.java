package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import com.example.demo.entity.PurchaseRequest;

public interface ProcurementService {

	PurchaseRequest createPurchaseRequest(PurchaseRequest purchaseRequest);

	PurchaseRequest findByPurchaseRequestId(UUID purchaseId);

	List<PurchaseRequest> findAllPurchaseRequest(UUID requesterId);

	String updateStatus(UUID purchaseId);

}

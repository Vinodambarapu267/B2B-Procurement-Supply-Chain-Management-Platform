package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PurchaseRequest;

@Service
public interface ProcurementService {

	PurchaseRequest createPurchaseRequest(PurchaseRequest purchaseRequest);

	PurchaseRequest findByPurchaseRequestId(UUID purchaseId);

	List<PurchaseRequest> findAllPurchaseRequest(UUID requesterId);

	String updateStatus(UUID purchaseId);

}

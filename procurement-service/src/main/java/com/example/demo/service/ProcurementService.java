package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PurchaseRequest;

@Service
public interface ProcurementService {

	PurchaseRequest createPurchaseRequest(PurchaseRequest purchaseRequest, Authentication authentication);

	PurchaseRequest findByPurchaseRequestId(UUID purchaseId, Authentication authentication);

	List<PurchaseRequest> findAllPurchaseRequest(Authentication authentication);

	String cancelPurchaseRequest(UUID purchaseId, Authentication authentication );

}

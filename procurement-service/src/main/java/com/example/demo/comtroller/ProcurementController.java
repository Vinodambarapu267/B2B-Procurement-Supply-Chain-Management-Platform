package com.example.demo.comtroller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PurchaseRequest;
import com.example.demo.service.ProcurementService;

import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase-requests")
public class ProcurementController {
	private final ProcurementService procurementService;

	@PostMapping("/createpurchaserequest")
	public PurchaseRequest createPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		System.out.println(purchaseRequest);
		return procurementService.createPurchaseRequest(purchaseRequest);
	}

	@GetMapping("/{purchaseId}")
	public PurchaseRequest findBypruchaseId(@PathVariable UUID purchaseId) {
		return procurementService.findByPurchaseRequestId(purchaseId);
	}

	@GetMapping("/{requesterId}/all")
	public List<PurchaseRequest> findAllPurchaseRequest(@PathVariable UUID requesterId) {
		return procurementService.findAllPurchaseRequest(requesterId);
	}
}

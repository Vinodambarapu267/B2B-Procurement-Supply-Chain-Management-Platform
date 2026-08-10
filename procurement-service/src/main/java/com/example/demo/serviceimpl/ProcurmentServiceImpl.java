package com.example.demo.serviceimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PurchaseItem;
import com.example.demo.entity.PurchaseRequest;
import com.example.demo.repository.PurchaseRequestRepository;
import com.example.demo.service.ProcurementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcurmentServiceImpl implements ProcurementService {
	private final PurchaseRequestRepository repository;

	@Override
	public PurchaseRequest createPurchaseRequest(PurchaseRequest purchaseRequest) {
		PurchaseRequest newPurchaseRequest = new PurchaseRequest();
		newPurchaseRequest.setRequesterId(purchaseRequest.getRequesterId());
		newPurchaseRequest.setStatus(purchaseRequest.getStatus());
		Double totalEstimation = 0.0;
		List<PurchaseItem> items = purchaseRequest.getItems();
		for (PurchaseItem item : items) {
			totalEstimation += item.getUnitPrice() * item.getQuantity();
		}
		newPurchaseRequest.setItems(items);
		newPurchaseRequest.setTotalEstimation(totalEstimation);
		return repository.save(newPurchaseRequest);
	}

	@Override
	public PurchaseRequest findByPurchaseRequestId(UUID purchaseId) {
		return repository.findById(purchaseId).orElseThrow(() -> new RuntimeException("This PR Not found"));
	}

	@Override
	public List<PurchaseRequest> findAllPurchaseRequest(UUID requesterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateStatus(UUID purchaseId) {
		// TODO Auto-generated method stub
		return null;
	}

}

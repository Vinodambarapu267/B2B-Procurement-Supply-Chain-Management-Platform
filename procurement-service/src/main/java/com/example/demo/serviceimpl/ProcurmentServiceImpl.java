package com.example.demo.serviceimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PurchaseItem;
import com.example.demo.entity.PurchaseRequest;
import com.example.demo.repository.PurchaseRequestRepository;
import com.example.demo.service.ProcurementService;
import com.example.demo.utility.PurchaseStatus;

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
		return repository.findByrequesterId(requesterId);
	}

	@Override
	@Transactional
	@Modifying
	public String updateStatus(UUID purchaseId, PurchaseStatus status) {
		PurchaseRequest purchaseRequest = repository.findById(purchaseId)
				.orElseThrow(() -> new RuntimeException("This PR Not found"));
		purchaseRequest.setStatus(status);
		Integer version = purchaseRequest.getVersion() + 1;
		purchaseRequest.setVersion(version);
		repository.save(purchaseRequest);
		return "UPdated status to " + status;
	}

}

package com.example.demo.serviceimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PurchaseRequest;
import com.example.demo.service.ProcurementService;
@Service
public class ProcurmentServiceImpl implements ProcurementService{

	@Override
	public PurchaseRequest createPurchaseRequest(PurchaseRequest purchaseRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseRequest findByPurchaseRequestId(UUID purchaseId) {
		// TODO Auto-generated method stub
		return null;
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

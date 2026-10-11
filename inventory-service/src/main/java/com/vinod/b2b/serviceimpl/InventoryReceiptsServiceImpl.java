package com.vinod.b2b.serviceimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.InventoryReceipt;
import com.vinod.b2b.repository.InventoryReceiptsRepository;
import com.vinod.b2b.service.InventoryReceiptsService;

@Service
public class InventoryReceiptsServiceImpl implements InventoryReceiptsService {
	private final InventoryReceiptsRepository receiptsRepository;

	public InventoryReceiptsServiceImpl(InventoryReceiptsRepository receiptsRepository) {
		super();
		this.receiptsRepository = receiptsRepository;
	}

	@Override
	public InventoryReceipt addInventoryReceipt(InventoryReceipt inventoryReceipt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryReceipt getInventoryReceiptById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InventoryReceipt> getAllDiscrepancies() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.vinod.b2b.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;

import com.vinod.b2b.entity.InventoryReceipt;

public interface InventoryReceiptsService {
	public InventoryReceipt addInventoryReceipt(InventoryReceipt inventoryReceipt,Authentication authentication);

	public InventoryReceipt getInventoryReceiptById(UUID id);

	public List<InventoryReceipt> getAllDiscrepancies();
}

package com.vinod.b2b.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.InventoryItem;
import com.vinod.b2b.entity.InventoryReceipt;
import com.vinod.b2b.exceptions.InventoryItemNotFoundException;
import com.vinod.b2b.exceptions.InventoryReceiptNotFoundException;
import com.vinod.b2b.repository.InventoryItemsRepository;
import com.vinod.b2b.repository.InventoryReceiptsRepository;
import com.vinod.b2b.service.InventoryReceiptsService;

@Service
public class InventoryReceiptsServiceImpl implements InventoryReceiptsService {
	private final InventoryReceiptsRepository receiptsRepository;

	private final InventoryItemsRepository inventoryItemsRepository;

	public InventoryReceiptsServiceImpl(InventoryReceiptsRepository receiptsRepository,
			InventoryItemsRepository InventoryItemsRepository) {
		super();
		this.receiptsRepository = receiptsRepository;
		this.inventoryItemsRepository = InventoryItemsRepository;
	}

	@Override
	public InventoryReceipt addInventoryReceipt(InventoryReceipt inventoryReceipt, Authentication authentication) {

		UUID itemId = inventoryReceipt.getItemId().getItemId();
		InventoryItem inventoryItem = inventoryItemsRepository.findById(itemId)
				.orElseThrow(() -> new InventoryItemNotFoundException("Inventory Item Not Found" + itemId));
		Integer quantitityReceived = inventoryReceipt.getQuantityReceived();

		if (quantitityReceived <= 0) {
			throw new RuntimeException("Quantity received must be greater than zero");
		}

		inventoryItem.setQuantityOnHand(inventoryItem.getQuantityOnHand() + quantitityReceived);
		inventoryReceipt.setItemId(inventoryItem);
		if (inventoryReceipt.getQuantityExpected() != inventoryReceipt.getQuantityReceived()) {
			inventoryReceipt.setDiscrepancyFlag(true);
		}
		inventoryReceipt.setReceivedBy(authentication.getName());
		inventoryItemsRepository.save(inventoryItem);
		return receiptsRepository.save(inventoryReceipt);
	}

	@Override
	public InventoryReceipt getInventoryReceiptById(UUID id) {
		InventoryReceipt receipt = receiptsRepository.findById(id)
				.orElseThrow(() -> new InventoryReceiptNotFoundException("Receipt not Found :" + id));
		return receipt;
	}

	@Override
	public List<InventoryReceipt> getAllDiscrepancies() {
		List<InventoryReceipt> inventoryReceipts = receiptsRepository.findAll();
		List<InventoryReceipt> discrepencies = new ArrayList<>();
		for (InventoryReceipt receipt : inventoryReceipts) {
			if (receipt.getDiscrepancyFlag()) {
				discrepencies.add(receipt);
			}
		}
		if (discrepencies.isEmpty()) {
			throw new InventoryReceiptNotFoundException("No discrepancies list");
		}
		return discrepencies;
	}

}

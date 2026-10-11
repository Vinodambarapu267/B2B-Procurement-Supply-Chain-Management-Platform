package com.vinod.b2b.serviceimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.InventoryItem;
import com.vinod.b2b.entity.InventoryReceipt;
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
	public InventoryReceipt addInventoryReceipt(InventoryReceipt inventoryReceipt) {
		UUID itemId = inventoryReceipt.getItemId().getItemId();
		InventoryItem inventoryItem = inventoryItemsRepository.findById(itemId)
				.orElseThrow(() -> new RuntimeException("InventoryItem Not Found" + itemId));
		Integer quantitityReceived = inventoryReceipt.getQuantityReceived();
		if (quantitityReceived <= 0) {
			throw new IllegalArgumentException("Quantity received must be greater than zero");
		}
		inventoryItem.setQuantityOnHand(inventoryItem.getQuantityOnHand() + quantitityReceived);
		inventoryReceipt.setItemId(inventoryItem);
		if (inventoryReceipt.getQuantityExpected() != inventoryReceipt.getQuantityReceived()) {
			inventoryReceipt.setDiscrepancyFlag(true);
		}
		//inventoryReceipt.setReceivedBy(inventoryReceipt.getReceivedBy());
		inventoryItemsRepository.save(inventoryItem);
		return receiptsRepository.save(inventoryReceipt);
	}

	@Override
	public InventoryReceipt getInventoryReceiptById(UUID id) {
		InventoryReceipt receipt = receiptsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Receipt not Found :" + id));
		return receipt;
	}

	@Override
	public List<InventoryReceipt> getAllDiscrepancies() {
		return null;
	}

}

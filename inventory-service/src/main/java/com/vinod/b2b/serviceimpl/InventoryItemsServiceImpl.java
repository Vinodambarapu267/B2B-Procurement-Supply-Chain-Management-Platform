package com.vinod.b2b.serviceimpl;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.vinod.b2b.entity.InventoryItem;
import com.vinod.b2b.repository.InventoryItemsRepository;
import com.vinod.b2b.service.InventoryItemService;

@Service
public class InventoryItemsServiceImpl implements InventoryItemService {
	private InventoryItemsRepository itemsRepository;

	public InventoryItemsServiceImpl(InventoryItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	@Override
	public InventoryItem addInventoryItem(InventoryItem item) {
		InventoryItem save = itemsRepository.save(item);
		return save;
	}

	@Override
	public InventoryItem getInventoryItemBySku(String sku) {
		InventoryItem inventoryItem = itemsRepository.findBySku(sku)
				.orElseThrow(() -> new RuntimeException("Item not found : " + sku));
		return inventoryItem;
	}

	@Override
	@Modifying
	public String updateInventoryThreshold(String sku, Integer threshold) {
		InventoryItem inventoryItem = itemsRepository.findBySku(sku)
				.orElseThrow(() -> new RuntimeException("Item not found : " + sku));
		inventoryItem.setRecordThreshold(threshold);
		InventoryItem save = itemsRepository.save(inventoryItem);
		return "updated the threshold value : " + save.getRecordThreshold();

	}

	@Override
	public List<InventoryItem> getAllInventoryItems() {
		List<InventoryItem> inventoryItems = itemsRepository.findAll();
		if (inventoryItems.isEmpty())
			throw new RuntimeException(" Inventory items are empty");
		return inventoryItems;
	}

}

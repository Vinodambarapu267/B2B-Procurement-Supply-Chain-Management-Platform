package com.vinod.b2b.serviceimpl;

import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateInventoryThreshold(String sku) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InventoryItem> getAllInventoryItems() {
		// TODO Auto-generated method stub
		return null;
	}

}

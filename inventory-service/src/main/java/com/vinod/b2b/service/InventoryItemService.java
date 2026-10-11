package com.vinod.b2b.service;

import java.util.List;

import com.vinod.b2b.entity.InventoryItem;

public interface InventoryItemService {
	public InventoryItem addInventoryItem(InventoryItem item);

	public InventoryItem getInventoryItemBySku(String sku);

	public String updateInventoryThreshold(String sku);

	public List<InventoryItem> getAllInventoryItems();

}

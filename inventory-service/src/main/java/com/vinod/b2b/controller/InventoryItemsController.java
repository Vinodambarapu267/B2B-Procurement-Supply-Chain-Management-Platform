package com.vinod.b2b.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.InventoryItem;
import com.vinod.b2b.service.InventoryItemService;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryItemsController {
	private final InventoryItemService inventoryItemService;

	public InventoryItemsController(InventoryItemService inventoryItemService) {
		super();
		this.inventoryItemService = inventoryItemService;
	}

	@PostMapping()
	public InventoryItem addInventoryItem(@RequestBody InventoryItem item) {
		return inventoryItemService.addInventoryItem(item);

	}

	@GetMapping("/{sku}")
	public InventoryItem getBySku(@PathVariable String sku) {
		return inventoryItemService.getInventoryItemBySku(sku);
	}
}

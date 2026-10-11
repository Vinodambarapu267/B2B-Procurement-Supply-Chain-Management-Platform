package com.vinod.b2b.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.InventoryReceipt;
import com.vinod.b2b.service.InventoryReceiptsService;

@RestController
@RequestMapping("/api/v1/inventory/receipts")
public class InventoryReceiptsController {
	private final InventoryReceiptsService inventoryReceiptsService;

	public InventoryReceiptsController(InventoryReceiptsService inventoryReceiptsService) {
		super();
		this.inventoryReceiptsService = inventoryReceiptsService;
	}

	@PostMapping
	public InventoryReceipt addInventoryReceipt(@RequestBody InventoryReceipt inventoryReceipt) {
		return inventoryReceiptsService.addInventoryReceipt(inventoryReceipt);

	}
}

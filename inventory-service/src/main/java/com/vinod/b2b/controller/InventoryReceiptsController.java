package com.vinod.b2b.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/{id}")
	public InventoryReceipt getInventoryReceiptById(@PathVariable UUID id) {
		return inventoryReceiptsService.getInventoryReceiptById(id);

	}

	@GetMapping("/discrepancies")
	public List<InventoryReceipt> getListOfDiscrepancies() {
		return inventoryReceiptsService.getAllDiscrepancies();
	}
}

package com.vinod.b2b.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.InventoryItem;
import com.vinod.b2b.service.InventoryItemService;
import com.vinod.b2b.utility.ResponseMessage;
import com.vinod.b2b.utility.ResponseStatus;

@RestController
@RequestMapping("/api/v1/inventory/items")
public class InventoryItemsController {
	private final InventoryItemService inventoryItemService;

	public InventoryItemsController(InventoryItemService inventoryItemService) {
		super();
		this.inventoryItemService = inventoryItemService;
	}

	@PostMapping()
	public ResponseEntity<?> addInventoryItem(@RequestBody InventoryItem item) {
		InventoryItem inventoryItem = inventoryItemService.addInventoryItem(item);
		if (inventoryItem == null) {
			return ResponseEntity.ok(
					ResponseMessage.builder()
					.message("Inventory Item adding failed..")
					.status(ResponseStatus.FAILURE.name())
					.statuscode(HttpURLConnection.HTTP_NOT_FOUND)
					.build()
					);

		}
		return ResponseEntity.ok(
				ResponseMessage.builder()
				.message("Inventory Item added Successfull..")
				.status(ResponseStatus.SUCCESS.name())
				.statuscode(HttpURLConnection.HTTP_CREATED)
				.data(inventoryItem)
				.build()
				);
	}

	@GetMapping("/{sku}")
	public ResponseEntity<?> getBySku(@PathVariable String sku) {
		InventoryItem inventoryItem = inventoryItemService.getInventoryItemBySku(sku);
		if (inventoryItem == null) {
			return ResponseEntity.ok(ResponseMessage.builder()
					.message("Inventory Item retrival failed")
					.status(ResponseStatus.FAILURE.name())
					.statuscode(HttpURLConnection.HTTP_NO_CONTENT)
					.build()
					);

		}
		return ResponseEntity.ok(
				ResponseMessage.builder()
				.message("Inventory Item retrival Successfull..")
				.status(ResponseStatus.SUCCESS.name())
				.statuscode(HttpURLConnection.HTTP_OK)
				.data(inventoryItem)
				.build()
				);
	}

	@PutMapping("/{sku}/threshold")
	public String updateInventoryItemThreshold(@PathVariable String sku, @RequestParam Integer threshold) {
		return inventoryItemService.updateInventoryThreshold(sku, threshold);
	}

	@GetMapping
	public ResponseEntity<?> findAllInventoryItems() {
		List<InventoryItem> inventoryItems = inventoryItemService.getAllInventoryItems();
		if (inventoryItems == null) {
			return ResponseEntity.ok(
					ResponseMessage.builder()
					.message("Inventory Item retrival failed..")
					.status(ResponseStatus.FAILURE.name())
					.statuscode(HttpURLConnection.HTTP_NOT_FOUND)
					.build()
					);

		}
		return ResponseEntity.ok(
				ResponseMessage.builder()
				.message("All Inventory Item retrived Successful..")
				.status(ResponseStatus.SUCCESS.name())
				.statuscode(HttpURLConnection.HTTP_ACCEPTED)
				.data(inventoryItems)
				.build()
				);
	}
}

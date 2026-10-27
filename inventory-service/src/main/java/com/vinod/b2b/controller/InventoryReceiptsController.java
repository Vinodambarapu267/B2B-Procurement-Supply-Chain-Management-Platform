package com.vinod.b2b.controller;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.InventoryReceipt;
import com.vinod.b2b.service.InventoryReceiptsService;
import com.vinod.b2b.utility.ResponseMessage;
import com.vinod.b2b.utility.ResponseStatus;

@RestController
@RequestMapping("/api/v1/inventory/receipts")
public class InventoryReceiptsController {
	private final InventoryReceiptsService inventoryReceiptsService;

	public InventoryReceiptsController(InventoryReceiptsService inventoryReceiptsService) {
		super();
		this.inventoryReceiptsService = inventoryReceiptsService;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('GOODS_RECEIPT_CREATE')")
	public ResponseEntity<?> addInventoryReceipt(@RequestBody InventoryReceipt inventoryReceipt,Authentication authentication) {
		 InventoryReceipt receipt = inventoryReceiptsService.addInventoryReceipt(inventoryReceipt,authentication);
			if (receipt == null) {
				return ResponseEntity.ok(
						ResponseMessage.builder()
						.message("Inventory Receipt creating failed..")
						.status(ResponseStatus.FAILURE.name())
						.statuscode(HttpURLConnection.HTTP_NOT_FOUND)
						.data(receipt)
						.build()
						);

			}
			return ResponseEntity.ok(
					ResponseMessage.builder()
					.message("Inventory Receipt created Successfull..")
					.status(ResponseStatus.SUCCESS.name())
					.statuscode(HttpURLConnection.HTTP_CREATED)
					.data(receipt)
					.build()
					);

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('INVENTORY_RECEIPTS_VIEW')")
	public ResponseEntity<?> getInventoryReceiptById(@PathVariable UUID id) {
		 InventoryReceipt inventoryReceipt = inventoryReceiptsService.getInventoryReceiptById(id);
		 if (inventoryReceipt == null) {
				return ResponseEntity.ok(
						ResponseMessage.builder()
						.message("Inventory Receipt retrival failed..")
						.status(ResponseStatus.FAILURE.name())
						.statuscode(HttpURLConnection.HTTP_NOT_FOUND)
						.build()
						);

			}
			return ResponseEntity.ok(
					ResponseMessage.builder()
					.message("Inventory Receipt retrived Successfull..")
					.status(ResponseStatus.SUCCESS.name())
					.statuscode(HttpURLConnection.HTTP_ACCEPTED)
					.data(inventoryReceipt)
					.build()
					);


	}

	@GetMapping("/discrepancies")
	@PreAuthorize("hasAuthority('INVENTORY_DISCREPANCY_REVIEW')")
	public ResponseEntity<?> getListOfDiscrepancies() {
		 List<InventoryReceipt> allDiscrepancies = inventoryReceiptsService.getAllDiscrepancies();
		 if (allDiscrepancies == null) {
				return ResponseEntity.ok(
						ResponseMessage.builder()
						.message("allDiscrepancies Receipt retrival failed..")
						.status(ResponseStatus.FAILURE.name())
						.statuscode(HttpURLConnection.HTTP_NO_CONTENT)
						.build()
						);

			}
			return ResponseEntity.ok(
					ResponseMessage.builder()
					.message("allDiscrepancies Receipt retrived Successfull..")
					.status(ResponseStatus.SUCCESS.name())
					.statuscode(HttpURLConnection.HTTP_OK)
					.data(allDiscrepancies)
					.build()
					);
	}
}

package com.example.demo.comtroller;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PurchaseRequest;
import com.example.demo.service.ProcurementService;
import com.example.demo.utility.PurchaseStatus;
import com.example.demo.utility.ResponseMessage;
import com.example.demo.utility.ResponseStatus;

import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase-requests")
public class ProcurementController {
	private final ProcurementService procurementService;

	@PostMapping("/createpurchaserequest")
	public ResponseEntity<?> createPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
	
		 PurchaseRequest request = procurementService.createPurchaseRequest(purchaseRequest);
		 if(request==null) {
			 return ResponseEntity.ok(ResponseMessage.builder()
					 .statuscode(HttpURLConnection.HTTP_NOT_FOUND)
					 .status(ResponseStatus.FAILURE.name())
					 .message("Purchase Request failed..")
					 .build());
		 }
		 return ResponseEntity.ok(ResponseMessage.builder()
				 .statuscode(HttpURLConnection.HTTP_CREATED)
				 .status(ResponseStatus.SUCCESS.name())
				 .message("Purchase Request Successfull")
				 .data(request)
				 .build());
	}

	@GetMapping("/{purchaseId}")
	public ResponseEntity<?> findBypruchaseId(@PathVariable UUID purchaseId) {
		 PurchaseRequest purchaseRequest= procurementService.findByPurchaseRequestId(purchaseId);
		 if(purchaseRequest==null) {
			 return ResponseEntity.ok(ResponseMessage.builder()
					 .statuscode(HttpURLConnection.HTTP_NOT_FOUND)
					 .status(ResponseStatus.FAILURE.name())
					 .message("Purchase Request retrived failed..")
					 .build());
		 }
		 return ResponseEntity.ok(ResponseMessage.builder()
				 .statuscode(HttpURLConnection.HTTP_CREATED)
				 .status(ResponseStatus.SUCCESS.name())
				 .message("Purchase Request retrived Successfull")
				 .data(purchaseRequest)
				 .build());
	}

	@GetMapping("/{requesterId}/all")
	public ResponseEntity<?> findAllPurchaseRequest(@PathVariable UUID requesterId) {
		 List<PurchaseRequest> allPurchaseRequest = procurementService.findAllPurchaseRequest(requesterId);
		 if(allPurchaseRequest==null) {
			 return ResponseEntity.ok(ResponseMessage.builder()
					 .statuscode(HttpURLConnection.HTTP_NOT_FOUND)
					 .status(ResponseStatus.FAILURE.name())
					 .message("All Purchase Request retrived failed..")
					 .build());
		 }
		 return ResponseEntity.ok(ResponseMessage.builder()
				 .statuscode(HttpURLConnection.HTTP_CREATED)
				 .status(ResponseStatus.SUCCESS.name())
				 .message("All Purchase Request retrived Successfull")
				 .data(allPurchaseRequest)
				 .build());
	}

	@PatchMapping("/{id}/status")
	public String updateStatus(@PathVariable UUID id, @RequestParam PurchaseStatus status) {
		return procurementService.updateStatus(id, status);
	}
}

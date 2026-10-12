package com.vinod.b2b.controller;

import java.net.HttpURLConnection;
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

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.service.PurchaseOrderService;
import com.vinod.b2b.utility.OrderStatus;
import com.vinod.b2b.utility.ResponseMessage;
import com.vinod.b2b.utility.ResponseStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
	private final PurchaseOrderService purchaseOrderService;

	@PostMapping
	public ResponseEntity<?> generatePurchaseOrder(@RequestBody PurchaseOrders purchaseOrders) {
		 PurchaseOrders purchaseOrder = purchaseOrderService.generatePurchaseOrder(purchaseOrders);
		 if(purchaseOrder==null) {
			 return ResponseEntity.ok(
					 ResponseMessage.builder()
					 .message("New PurchaseOrder generated failed..")
					 .status(ResponseStatus.FAILED.name())
					 .statusCode(HttpURLConnection.HTTP_NO_CONTENT)
					 .build()
					 );
		 }
		 return ResponseEntity.ok(
				 ResponseMessage.builder()
				 .message("New PurchaseOrder generated Successfully")
				 .status(ResponseStatus.SUCCESS.name())
				 .statusCode(HttpURLConnection.HTTP_CREATED)
				 .data(purchaseOrder)
				 .build()
				 );

	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<?> updatePurchaseOrderStatus(@PathVariable(name = "id") UUID purchaseOrderId,
			@RequestParam OrderStatus status) {
		 PurchaseOrders updatePurchaseOrderstatus = purchaseOrderService.updatePurchaseOrderstatus(purchaseOrderId, status);
		if(updatePurchaseOrderstatus==null) {
			 return ResponseEntity.ok(
					 ResponseMessage.builder()
					 .message("PurchaseOrder update status failed..")
					 .status(ResponseStatus.FAILED.name())
					 .statusCode(HttpURLConnection.HTTP_NOT_MODIFIED)
					 .build()
					 );
		 }
		 return ResponseEntity.ok(
				 ResponseMessage.builder()
				 .message("PurchaseOrder status updated Successfully")
				 .status(ResponseStatus.SUCCESS.name())
				 .statusCode(HttpURLConnection.HTTP_ACCEPTED)
				 .data(updatePurchaseOrderstatus)
				 .build()
				 );
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> findByPurchaseOrders(@PathVariable(name="id") UUID purchaseOrderId) {
		 PurchaseOrders purchaseOrder = purchaseOrderService.getPurchaseOrder(purchaseOrderId);
		 if(purchaseOrder==null) {
			 return ResponseEntity.ok(
					 ResponseMessage.builder()
					 .message("PurchaseOrder retriving failed..")
					 .status(ResponseStatus.FAILED.name())
					 .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
					
					 .build()
					 );
		 }
		 return ResponseEntity.ok(
				 ResponseMessage.builder()
				 .message("PurchaseOrder retrived Successfully")
				 .status(ResponseStatus.SUCCESS.name())
				 .statusCode(HttpURLConnection.HTTP_OK)
				 .data(purchaseOrder)
				 .build()
				 );	
	}
}

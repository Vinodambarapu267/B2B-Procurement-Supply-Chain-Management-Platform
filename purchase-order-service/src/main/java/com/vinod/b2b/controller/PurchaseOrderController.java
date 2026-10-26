package com.vinod.b2b.controller;

import java.net.HttpURLConnection;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.PurchaseOrders;
import com.vinod.b2b.service.PurchaseOrderService;
import com.vinod.b2b.utility.ResponseMessage;
import com.vinod.b2b.utility.ResponseStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
	private final PurchaseOrderService purchaseOrderService;

	@PostMapping
	@PreAuthorize("hasAuthority('PURCHASE_ORDER_CREATE')")
	public ResponseEntity<?> generatePurchaseOrder(@RequestBody PurchaseOrders purchaseOrders,
			Authentication authentication) {
		PurchaseOrders purchaseOrder = purchaseOrderService.generatePurchaseOrder(purchaseOrders, authentication);
		if (purchaseOrder == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("New PurchaseOrder generated failed..")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NO_CONTENT).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("New PurchaseOrder generated Successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_CREATED).data(purchaseOrder)
				.build());

	}

	@PatchMapping("/{id}/sent")
	public ResponseEntity<?> sentPurchaseOrder(@PathVariable(name = "id") UUID purchaseOrderId,
			Authentication authentication) {
		PurchaseOrders sentPurchaseOrder = purchaseOrderService.sentPurchaseOrder(purchaseOrderId, authentication);
		if (sentPurchaseOrder == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder sent failed..")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NOT_MODIFIED).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder sent Successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_ACCEPTED)
				.data(sentPurchaseOrder).build());
	}

	@PatchMapping("/{id}/accept")
	@PreAuthorize("hasAuthority('PURCHASE_ORDER_SUPPLIER_ACTION')")
	public ResponseEntity<?> acceptPurchaseOrder(@PathVariable("id") UUID purchaseOrderId,
			Authentication authentication) {
		PurchaseOrders acceptPurchaseOrder = purchaseOrderService.acceptPurchaseOrder(purchaseOrderId, authentication);
		if (acceptPurchaseOrder == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder accept failed..")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NOT_MODIFIED).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder accepted  Successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_ACCEPTED)
				.data(acceptPurchaseOrder).build());
	}

	@PatchMapping("/{id}/dispatch")
	@PreAuthorize("hasAuthority('PURCHASE_ORDER_SUPPLIER_ACTION')")
	public ResponseEntity<?> dispatchPurchaseOrder(@PathVariable("id") UUID purchaseOrderId,
			Authentication authentication) {
		PurchaseOrders dispatchPurchaseOrder = purchaseOrderService.dispatchPurchaseOrder(purchaseOrderId,
				authentication);
		if (dispatchPurchaseOrder == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder dispatch failed..")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NOT_MODIFIED).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder dispatched Successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_ACCEPTED)
				.data(dispatchPurchaseOrder).build());
	}

	@PatchMapping("/{id}/complete")
	@PreAuthorize("hasAuthority('PURCHASE_ORDER_COMPLETE')")
	public ResponseEntity<?> completePurchaseOrder(@PathVariable("id") UUID purchaseOrderId) {
		PurchaseOrders completePurchaseOrder = purchaseOrderService.completePurchaseOrder(purchaseOrderId);
		if (completePurchaseOrder == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder completing failed..")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_NOT_MODIFIED).build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder completed Successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_ACCEPTED)
				.data(completePurchaseOrder).build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findByPurchaseOrders(@PathVariable(name = "id") UUID purchaseOrderId,
			Authentication authentication) {
		PurchaseOrders purchaseOrder = purchaseOrderService.getPurchaseOrder(purchaseOrderId, authentication);
		if (purchaseOrder == null) {
			return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder retriving failed..")
					.status(ResponseStatus.FAILED.name()).statusCode(HttpURLConnection.HTTP_BAD_REQUEST)

					.build());
		}
		return ResponseEntity.ok(ResponseMessage.builder().message("PurchaseOrder retrived Successfully")
				.status(ResponseStatus.SUCCESS.name()).statusCode(HttpURLConnection.HTTP_OK).data(purchaseOrder)
				.build());
	}
}

package com.vinod.b2b.controller;

import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.entity.Supplier;
import com.vinod.b2b.service.SupplierService;
import com.vinod.b2b.utility.ResponseMessage;
import com.vinod.b2b.utility.ResponseStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {
	private final SupplierService supplierService;

	@PostMapping
	public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier) {
		 Supplier newSupplier = supplierService.createSupplier(supplier);
		 if(newSupplier==null) {
			 return ResponseEntity.ok(ResponseMessage.builder()
					 .message("creating New Supplier failed...")
					 .status(ResponseStatus.FAILED.name())
					 .statusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
					 .build()
					 );
		 }
		 return ResponseEntity.ok(ResponseMessage.builder()
				 .message("New Supplier Created Successfully ...")
				 .status(ResponseStatus.SUCCESS.name())
				 .statusCode(HttpURLConnection.HTTP_CREATED)
				 .data(newSupplier)
				 .build()
				 );
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<?> updateStatus(@PathVariable(name = "id") UUID supplierId) {
		 Supplier updateSupplierStatus = supplierService.updateSupplierStatus(supplierId);
		 if(updateSupplierStatus==null) {
			 return ResponseEntity.ok(ResponseMessage.builder()
					 .message(" Supplier updatining failed...")
					 .status(ResponseStatus.FAILED.name())
					 .statusCode(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
					 .build()
					 );
		 }
		 return ResponseEntity.ok(ResponseMessage.builder()
				 .message("Supplier updated successfully ...")
				 .status(ResponseStatus.SUCCESS.name())
				 .statusCode(HttpURLConnection.HTTP_CREATED)
				 .data(updateSupplierStatus)
				 .build()
				 );
	}

	@GetMapping
	public Page<Supplier> getAllSuppliers(@RequestParam int page, @RequestParam String sortBy) {
		return supplierService.getAllSuppliers(page, sortBy);
	}
}

package com.vinod.b2b.controller;

import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinod.b2b.dto.SupplierDto;
import com.vinod.b2b.entity.Supplier;
import com.vinod.b2b.service.SupplierService;
import com.vinod.b2b.utility.ResponseMessage;
import com.vinod.b2b.utility.ResponseStatus;

import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {
	private final SupplierService supplierService;

	@PostMapping
	@PreAuthorize("hasAuthority('SUPPLIER_CREATE')")
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
	@PreAuthorize("hasAuthority('SUPPLIER_DEACTIVATE')")
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

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('SUPPLIER_UPDATE')")
	public ResponseEntity<?> updateSupplier(@PathVariable(name = "id") UUID supplierId, @RequestBody SupplierDto supplierDto) {
		 Supplier updateSupplier = supplierService.updateSupplier(supplierId, supplierDto);
		 if(updateSupplier==null) {
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
				 .data(updateSupplier)
				 .build()
				 );
	}
	@GetMapping("/{name}")
	public SupplierDto getSupplierByName(@PathVariable String name ) {
		return supplierService.findByNanme(name);
	}
	
	@GetMapping
	public Page<Supplier> getAllSuppliers(@RequestParam int page, @RequestParam String sortBy) {
		return supplierService.getAllSuppliers(page, sortBy);
	}
}

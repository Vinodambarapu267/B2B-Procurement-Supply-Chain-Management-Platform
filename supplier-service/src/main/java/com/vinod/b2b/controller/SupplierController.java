package com.vinod.b2b.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {
	private final SupplierService supplierService;

	@PostMapping
	public Supplier createSupplier(@RequestBody Supplier supplier) {
		return supplierService.createSupplier(supplier);
	}

	@PatchMapping("/{id}/status")
	public Supplier updateStatus(@PathVariable(name = "id") UUID supplierId) {
		return supplierService.updateSupplierStatus(supplierId);
	}

	@GetMapping
	public Page<Supplier> getAllSuppliers(@RequestParam int page, @RequestParam String sortBy) {
		return supplierService.getAllSuppliers(page, sortBy);
	}
}

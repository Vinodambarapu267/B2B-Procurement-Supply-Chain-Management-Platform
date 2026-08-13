package com.vinod.b2b.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
